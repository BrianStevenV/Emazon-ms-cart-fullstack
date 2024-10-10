package com.PowerUpFullStack.ms_cart.domain.usecase;

import com.PowerUpFullStack.ms_cart.domain.exception.CartDetailsNotFoundException;
import com.PowerUpFullStack.ms_cart.domain.exception.CartNotFoundException;
import com.PowerUpFullStack.ms_cart.domain.exception.OperationTypeNotPermissionException;
import com.PowerUpFullStack.ms_cart.domain.exception.ProductCategoryInvalidException;
import com.PowerUpFullStack.ms_cart.domain.exception.SupplyNextDateException;
import com.PowerUpFullStack.ms_cart.domain.model.AllCategories;
import com.PowerUpFullStack.ms_cart.domain.model.AmountStock;
import com.PowerUpFullStack.ms_cart.domain.model.Available;
import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.domain.api.ICartServicePort;
import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;
import com.PowerUpFullStack.ms_cart.domain.model.CustomPage;
import com.PowerUpFullStack.ms_cart.domain.model.FilterBy;
import com.PowerUpFullStack.ms_cart.domain.model.OperationType;
import com.PowerUpFullStack.ms_cart.domain.model.ProductIds;
import com.PowerUpFullStack.ms_cart.domain.model.SortDirection;
import com.PowerUpFullStack.ms_cart.domain.spi.ICartDetailsPersistencePort;
import com.PowerUpFullStack.ms_cart.domain.spi.ICartPersistencePort;
import com.PowerUpFullStack.ms_cart.domain.spi.IStockFeignClientPort;
import com.PowerUpFullStack.ms_cart.domain.spi.ISuppliesFeignClientPort;
import com.PowerUpFullStack.ms_cart.domain.usecase.utils.CartUseCaseUtils;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.PowerUpFullStack.ms_cart.domain.usecase.utils.ConstantsCartUseCase.MAX_INT_CATEGORY;
import static com.PowerUpFullStack.ms_cart.domain.usecase.utils.ConstantsCartUseCase.MIN_AMOUNT_TOTAL;


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
    public void addProductToCart(CartDetails cartDetailsToAdd, OperationType operationType) {

        long userId = cartUseCaseUtils.getIdFromAuthContext();

        Optional<Cart> cartFromDb = cartPersistencePort.findCartEntity(userId);

        if (!cartFromDb.isPresent()) {
            createNewCart(cartDetailsToAdd, userId);
        } else {
            cartDetailsPersistencePort.findByCartIdAndProductId(cartFromDb.get().getId(), cartDetailsToAdd.getProductId())
                    .ifPresentOrElse(
                            product -> {
                                updateExistingProduct(cartDetailsToAdd, product, operationType);
                                cartUseCaseUtils.setUpdateTimestamp(cartFromDb.get());
                                cartPersistencePort.saveCart(cartFromDb.get());
                            },
                            () -> {
                                addNewProduct(cartDetailsToAdd, cartFromDb.get().getId());
                                cartUseCaseUtils.setUpdateTimestamp(cartFromDb.get());
                                cartPersistencePort.saveCart(cartFromDb.get());
                            }
                    );
        }
    }


    private void createNewCart(CartDetails cartDetails, long userId) {

        validationStock(cartDetails.getProductId(), cartDetails.getAmount());



        Cart newCart = new Cart();
        newCart.setUserId(userId);
        cartUseCaseUtils.setCreationTimestamp(newCart);
        cartPersistencePort.saveCart(newCart);

        Optional<Cart> savedCart = cartPersistencePort.findCartEntity(userId);

        if(!savedCart.isPresent()){
            throw new CartNotFoundException();
        }

        cartDetails.setActive(true);
        cartDetails.setCartId(savedCart.get().getId());
        cartUseCaseUtils.setCreationTimestamp(cartDetails);
        cartDetailsPersistencePort.saveCartDetails(cartDetails);

    }



    private void updateExistingProduct(CartDetails cartDetailsToAdd, CartDetails productFromDb, OperationType operationType) {

        if(!productFromDb.getActive()){
            enableProduct(productFromDb);
            return;
        }


        int amountTotal;
        if (operationType.equals(OperationType.ADD)) {
            amountTotal = productFromDb.getAmount() + cartDetailsToAdd.getAmount();
        } else if (operationType.equals(OperationType.SUBTRACT)) {
            amountTotal = productFromDb.getAmount() - cartDetailsToAdd.getAmount();

            if (amountTotal <= MIN_AMOUNT_TOTAL) {
                disableProduct(productFromDb);
                return;
            }

        } else {
            throw new OperationTypeNotPermissionException();
        }

        validationStock(cartDetailsToAdd.getProductId(), amountTotal);
        productFromDb.setAmount(amountTotal);
        cartUseCaseUtils.setUpdateTimestamp(productFromDb);

        cartDetailsPersistencePort.saveCartDetails(productFromDb);
    }


        private boolean isStockAvailable(Long productId, int amount) {
        AmountStock amountStock = new AmountStock(productId, amount);
        Available isAvailable = stockFeignClientPort.amountStockAvailable(amountStock);
        return isAvailable.getAvailable();
    }

    private boolean isValidCategory(List<Long> productIds){
        AllCategories categories = stockFeignClientPort.getCategoriesNames(new ProductIds(productIds));

        categories.getCategories().values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(categoryId -> categoryId, Collectors.counting()))
                .forEach((categoryId, count) -> {
                    if (count > MAX_INT_CATEGORY) {
                        throw new ProductCategoryInvalidException();
                    }
                });

        return true;
    }

    @Override
    public void removeProductFromCart(long productId) {
        long userId = cartUseCaseUtils.getIdFromAuthContext();

        Optional<Cart> cartFromDb = cartPersistencePort.findCartEntity(userId);

        if(!cartFromDb.isPresent()){
            throw new CartNotFoundException();
        }

        cartDetailsPersistencePort.findByCartIdAndProductId(cartFromDb.get().getId(), productId)
                .ifPresentOrElse(
                        cartDetails -> {
                            disableProduct(cartDetails);
                        },
                        () -> {
                            throw new CartDetailsNotFoundException();
                        }
                );
    }


    @Override
    public CustomPage<Cart> getPaginationCartByAscAndDescByProductNameAndBrandNameAndCategoryName(SortDirection
                                                                                                              sortDirection,
                                                                                                  FilterBy filterBy) {
        return null;
    }

    public void addNewProduct(CartDetails product, long cartId){
        cartUseCaseUtils.setCreationTimestamp(product);
        product.setActive(true);
        product.setCartId(cartId);

        validationStock(product.getProductId(), product.getAmount());

        List<Long> productsIds = cartDetailsPersistencePort
                .findByCartIdListCartDetails(cartId)
                .stream()
                .map(CartDetails::getProductId)
                .collect(Collectors.toList());

        isValidCategory(productsIds);
        cartDetailsPersistencePort.saveCartDetails(product);
        
    }

    private void enableProduct(CartDetails productFromDbIsActiveFalse) {
        cartDetailsPersistencePort.enableCartDetailByCartIdAndProductId(
                productFromDbIsActiveFalse.getCartId(),
                productFromDbIsActiveFalse.getProductId()
        );

        updateAndSaveCartDetails(productFromDbIsActiveFalse);
    }



    private void validationStock(long productId, int amountTotal){
        if(!isStockAvailable(productId, amountTotal)) {
            throw new SupplyNextDateException(suppliesFeignClientPort.getNextDateSupply(productId));
        }
    }

    private void disableProduct(CartDetails cartDetails){
        updateAndSaveCartDetails(cartDetails);
        cartDetailsPersistencePort.deleteCartDetailsByCartIdAndProductId(cartDetails.getCartId(), cartDetails.getProductId());

    }

    private void updateAndSaveCartDetails(CartDetails cartDetails) {
        cartUseCaseUtils.setUpdateTimestamp(cartDetails);
        cartDetailsPersistencePort.saveCartDetails(cartDetails);
    }


}
