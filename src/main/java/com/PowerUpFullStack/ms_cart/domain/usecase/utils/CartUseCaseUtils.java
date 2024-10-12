package com.PowerUpFullStack.ms_cart.domain.usecase.utils;

import com.PowerUpFullStack.ms_cart.domain.exception.ObjectHasNotMethodException;
import com.PowerUpFullStack.ms_cart.domain.exception.ObjectMethodUpdateTimestampException;
import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.domain.model.CartDetailAndProduct;
import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;
import com.PowerUpFullStack.ms_cart.domain.model.FilterBy;
import com.PowerUpFullStack.ms_cart.domain.model.Product;
import com.PowerUpFullStack.ms_cart.domain.model.SortDirection;
import com.PowerUpFullStack.ms_cart.infrastructure.security.IAuthContext;

import java.lang.reflect.Method;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CartUseCaseUtils {
    private final IAuthContext authContext;

    public CartUseCaseUtils(IAuthContext authContext) {
        this.authContext = authContext;
    }



    public void setCreationTimestamp(Object cart) {
        LocalDateTime now = LocalDateTime.now();

        try{
            Method setCreatedAt = cart.getClass().getMethod("setCreatedAt", LocalDateTime.class);
            Method setUpdatedAt = cart.getClass().getMethod("setUpdatedAt", LocalDateTime.class);

            setCreatedAt.invoke(cart, now);
            setUpdatedAt.invoke(cart, now);
        } catch (Exception e) {
            throw new ObjectHasNotMethodException();
        }


    }


    public void setUpdateTimestamp(Object cart) {
        LocalDateTime now = LocalDateTime.now();
        try {
            Method setUpdatedAt = cart.getClass().getMethod("setUpdatedAt", LocalDateTime.class);
            setUpdatedAt.invoke(cart, now);
        } catch (Exception e) {
            throw new ObjectMethodUpdateTimestampException();
        }
    }

    public long getIdFromAuthContext() {
        return authContext.getAuthenticationId();
    }

    public static String buildSupplyMessage(Product product, int requiredAmount, String nextSupplyDate) {
        return new StringBuilder()
                .append("El producto ")
                .append(product.getName())
                .append(" no tiene suficiente stock. Actualmente hay disponible ")
                .append(product.getAmount())
                .append(" y su cantidad solicitada es ")
                .append(requiredAmount)
                .append(". Por favor, revise la cantidad solicitada o esté pendiente de la próxima fecha de suministro. Próxima fecha de suministro: ")
                .append(nextSupplyDate)
                .toString();
    }

    public static List<CartDetailAndProduct> sortCartDetails(
            List<CartDetailAndProduct> list,
            SortDirection sortDirection,
            FilterBy filterBy
    ) {
        Comparator<CartDetailAndProduct> comparator;

        switch (filterBy) {
            case PRODUCT:
                comparator = Comparator.comparing(CartDetailAndProduct::getName);
                break;
            case BRAND:
                comparator = Comparator.comparing(CartDetailAndProduct::getBrandName);
                break;
            case CATEGORY:
                comparator = Comparator.comparing(cartDetail ->
                        String.join(",", cartDetail.getCategoryNames())); // Concatenate categories for sorting
                break;
            default:
                throw new IllegalArgumentException("Invalid filter type");
        }

        // Apply sort direction
        if (sortDirection == SortDirection.DESC) {
            comparator = comparator.reversed();
        }

        return list.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

}
