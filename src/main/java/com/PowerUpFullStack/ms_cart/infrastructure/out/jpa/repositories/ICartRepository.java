package com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories;

import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories.utils.ConstantsRepository.PARAM_FIND_CART_ENTITY;
import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories.utils.ConstantsRepository.QUERY_FIND_CART_ENTITY;

@Repository
public interface ICartRepository extends JpaRepository<CartEntity, Long> {
    @Query(value = QUERY_FIND_CART_ENTITY, nativeQuery = true)
    Optional<CartEntity> findCartEntity(@Param(PARAM_FIND_CART_ENTITY) long userId);


}
