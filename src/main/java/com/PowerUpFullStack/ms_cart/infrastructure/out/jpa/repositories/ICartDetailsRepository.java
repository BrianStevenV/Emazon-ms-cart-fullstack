package com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories;

import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.CartDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories.utils.ConstantsRepository.QUERY_DISABLE_CART_DETAIL_BY_CART_ID_AND_PRODUCT_ID;
import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories.utils.ConstantsRepository.QUERY_ENABLE_CART_DETAIL_BY_CART_ID_AND_PRODUCT_ID;


@Repository
public interface ICartDetailsRepository extends JpaRepository<CartDetailsEntity, Long> {

    List<CartDetailsEntity> findAllByCartId(long cartId);

//    @Modifying
//    @Query("UPDATE CartDetailsEntity c SET c.isActive = false where c.cartId = :cartId AND c.productId = :productId")
////    @Query(QUERY_DISABLE_CART_DETAIL_BY_CART_ID_AND_PRODUCT_ID)
//    void disableCartDetailByCartIdAndProductId(long cartId, long productId);

    @Modifying
    @Query(value = "UPDATE cart_details SET is_active = false WHERE cart_id = :cartId AND product_id = :productId", nativeQuery = true)
    void disableCartDetailByCartIdAndProductId(@Param("cartId") long cartId, @Param("productId") long productId);


    @Modifying
//    @Query("UPDATE CartDetailsEntity c SET c.isActive = true where c.cartId = :cartId AND c.productId = :productId")
    @Query(QUERY_ENABLE_CART_DETAIL_BY_CART_ID_AND_PRODUCT_ID)
    void enableCartDetailByCartIdAndProductId(long cartId, long productId);

    Optional<CartDetailsEntity> findByCartIdAndProductId(long cartId, long productId);

}
