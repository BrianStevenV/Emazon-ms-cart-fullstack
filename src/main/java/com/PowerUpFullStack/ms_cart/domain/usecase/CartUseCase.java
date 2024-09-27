package com.PowerUpFullStack.ms_cart.domain.usecase;

import com.PowerUpFullStack.ms_cart.domain.exception.CartNotFoundException;
import com.PowerUpFullStack.ms_cart.domain.exception.ProductCategoryInvalidException;
import com.PowerUpFullStack.ms_cart.domain.model.AllCategories;
import com.PowerUpFullStack.ms_cart.domain.model.AmountStock;
import com.PowerUpFullStack.ms_cart.domain.model.Available;
import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.domain.api.ICartServicePort;
import com.PowerUpFullStack.ms_cart.domain.model.CartComplete;
import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;
import com.PowerUpFullStack.ms_cart.domain.model.ProductIds;
import com.PowerUpFullStack.ms_cart.domain.spi.ICartDetailsPersistencePort;
import com.PowerUpFullStack.ms_cart.domain.spi.ICartPersistencePort;
import com.PowerUpFullStack.ms_cart.domain.spi.IStockFeignClientPort;
import com.PowerUpFullStack.ms_cart.domain.spi.ISuppliesFeignClientPort;
import com.PowerUpFullStack.ms_cart.domain.usecase.utils.CartUseCaseUtils;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.PowerUpFullStack.ms_cart.domain.usecase.utils.ConstantsCartUseCase.INVALID_COUNT_CATEGORY;
import static com.PowerUpFullStack.ms_cart.domain.usecase.utils.ConstantsCartUseCase.MAX_INT_CATEGORY;
import static com.PowerUpFullStack.ms_cart.domain.usecase.utils.ConstantsCartUseCase.ROW_0;
import static com.PowerUpFullStack.ms_cart.domain.usecase.utils.ConstantsCartUseCase.ROW_1;
import static com.PowerUpFullStack.ms_cart.domain.usecase.utils.ConstantsCartUseCase.ROW_2;
import static com.PowerUpFullStack.ms_cart.domain.usecase.utils.ConstantsCartUseCase.ROW_3;
import static com.PowerUpFullStack.ms_cart.domain.usecase.utils.ConstantsCartUseCase.ROW_4;
import static com.PowerUpFullStack.ms_cart.domain.usecase.utils.ConstantsCartUseCase.ROW_5;
import static com.PowerUpFullStack.ms_cart.domain.usecase.utils.ConstantsCartUseCase.ROW_6;
import static com.PowerUpFullStack.ms_cart.domain.usecase.utils.ConstantsCartUseCase.ROW_7;


public class CartUseCase implements ICartServicePort {

    private final ICartPersistencePort cartPersistencePort;
    private final ICartDetailsPersistencePort cartDetailsPersistencePort;

    private final IStockFeignClientPort stockFeignClientPort;
    private final ISuppliesFeignClientPort suppliesFeignClientPort;

    private final CartUseCaseUtils cartUseCaseUtils;

    public CartUseCase(ICartPersistencePort cartPersistencePort,
                       ICartDetailsPersistencePort cartDetailsPersistencePort,
                       IStockFeignClientPort stockFeignClientPort,
                       ISuppliesFeignClientPort suppliesFeignClientPort,
                       CartUseCaseUtils cartUseCaseUtils) {
        this.cartPersistencePort = cartPersistencePort;
        this.cartDetailsPersistencePort = cartDetailsPersistencePort;
        this.stockFeignClientPort = stockFeignClientPort;
        this.suppliesFeignClientPort = suppliesFeignClientPort;
        this.cartUseCaseUtils = cartUseCaseUtils;
    }



    @Override
    public Cart addProductToCart(CartDetails cartDetails) {
        long userId = cartUseCaseUtils.getIdFromAuthContext();
        List<CartComplete> cartComplete = getUserCart(userId);

        if (!cartComplete.isEmpty()) {
            Optional<CartDetails> existingProduct = findExistingProductInCart(cartComplete, cartDetails.getProductId());
            if (existingProduct.isPresent()) {
                return updateExistingProduct(existingProduct.get(), cartDetails.getAmount(), cartComplete);
            }
        }

        return createNewCartWithProduct(userId, cartDetails);
    }

    private List<CartComplete> getUserCart(long userId) {
        List<Object[]> userCart = cartPersistencePort.findCartByUserId(userId);
        return userCart.stream()
                .map(this::mapToCartComplete)
                .collect(Collectors.toList());
    }

    private CartComplete mapToCartComplete(Object[] row) {
        CartComplete cartDetail = new CartComplete();
        cartDetail.setCartId((Long) row[ROW_0]);
        cartDetail.setUserIdFromCart((Long) row[ROW_1]);
        cartDetail.setCartDetailId((Long) row[ROW_2]);
        cartDetail.setAmount((Integer) row[ROW_3]);
        cartDetail.setCreatedAt((Timestamp) row[ROW_4]);
        cartDetail.setUpdatedAt((Timestamp) row[ROW_5]);
        cartDetail.setActive((Boolean) row[ROW_6]);
        cartDetail.setProductId((Long) row[ROW_7]);
        return cartDetail;
    }

    private Optional<CartDetails> findExistingProductInCart(List<CartComplete> cartComplete, Long productId) {
        return cartComplete.stream()
                .filter(cartDetail -> cartDetail.getProductId().equals(productId))
                .findFirst()
                .map(this::convertToCartDetails);
    }

    private CartDetails convertToCartDetails(CartComplete cartComplete) {
        CartDetails cartDetails = new CartDetails();
        cartDetails.setId(cartComplete.getCartDetailId());
        cartDetails.setProductId(cartComplete.getProductId());
        cartDetails.setAmount(cartComplete.getAmount());
        cartDetails.setCreatedAt(cartComplete.getCreatedAt().toLocalDateTime());
        cartDetails.setUpdatedAt(cartComplete.getUpdatedAt().toLocalDateTime());
        cartDetails.setActive(cartComplete.getActive());
        return cartDetails;
    }

    private Cart updateExistingProduct(CartDetails existingCartDetail, Integer amountToAdd, List<CartComplete> cartComplete) {
        cartUseCaseUtils.setUpdateTimestamp(existingCartDetail);
        int newAmount = existingCartDetail.getAmount() + amountToAdd;

        if (!isStockAvailable(existingCartDetail.getProductId(), newAmount)) {
            suppliesFeignClientPort.getNextDateSupply(existingCartDetail.getProductId());
        }

        Cart userCart = new Cart();
        userCart.setCartDetails(cartComplete.stream().map(this::convertToCartDetails).collect(Collectors.toList()));

        if (!isValidCategory(userCart)) {
            throw new ProductCategoryInvalidException();
        }

        existingCartDetail.setAmount(newAmount);
        cartPersistencePort.saveCart(userCart);
        return userCart;
    }

    private Cart createNewCartWithProduct(long userId, CartDetails cartDetails) {
        if (!isStockAvailable(cartDetails.getProductId(), cartDetails.getAmount())) {
            suppliesFeignClientPort.getNextDateSupply(cartDetails.getProductId());
            throw new IllegalStateException("Insufficient stock for product: " + cartDetails.getProductId());
        }

        cartUseCaseUtils.setCreationTimestamp(cartDetails);

        Cart newCart = new Cart();
        newCart.setUserId(userId);
        cartPersistencePort.saveCart(newCart);

        Cart cartSaved = cartPersistencePort.findById(userId).orElseThrow(() -> new CartNotFoundException());
        cartDetails.setActive(true);
        cartDetails.setCart(cartSaved);
        cartDetailsPersistencePort.saveCartDetails(cartDetails);

        return cartSaved;
    }

    private boolean isStockAvailable(Long productId, int amount) {
        AmountStock amountStock = new AmountStock(productId, amount);
        Available isAvailable = stockFeignClientPort.amountStockAvailable(amountStock);
        return isAvailable.getAvailable();
    }

    private boolean isValidCategory(Cart cart) {
        List<Long> productIds = cart.getCartDetails().stream()
                .map(CartDetails::getProductId)
                .collect(Collectors.toList());

        AllCategories categories = stockFeignClientPort.getCategoriesNames(new ProductIds(productIds));

        long invalidCategoryCount = categories.getCategories().values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(categoryId -> categoryId, Collectors.counting()))
                .values().stream()
                .filter(count -> count > MAX_INT_CATEGORY)
                .count();

        return invalidCategoryCount == INVALID_COUNT_CATEGORY;
    }



}
