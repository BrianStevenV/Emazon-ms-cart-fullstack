package com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories;

import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartRepository extends JpaRepository<CartEntity, Long> {

    @Query(value = "SELECT c.id AS cart_id, c.user_id, " +
            "cd.id AS cart_detail_id, cd.amount, cd.created_at, cd.updated_at, cd.is_active, cd.product_id " +
            "FROM cart c " +
            "LEFT JOIN cart_details cd ON c.id = cd.cart_id " +
            "WHERE c.user_id = :userId", nativeQuery = true)
    List<Object[]> findByUserId(@Param("userId") Long userId);

}
