package com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories;

import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.CartDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICartDetailsRepository extends JpaRepository<CartDetailsEntity, Long> {

    CartDetailsEntity findByCartId(long cartId);

}
