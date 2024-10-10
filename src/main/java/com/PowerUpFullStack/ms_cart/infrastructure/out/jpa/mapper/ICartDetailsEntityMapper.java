package com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.mapper;

import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.CartDetailsEntity;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Optional;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICartDetailsEntityMapper {

    @Mapping(target = "active", source = "isActive")  // Cambiado a 'active'
    CartDetails toCartDetails(CartDetailsEntity cartDetailsEntity);

    @Mapping(target = "isActive", source = "active")
    CartDetailsEntity toCartDetailsEntity(CartDetails cartDetails);

    List<CartDetails> toCartDetailsList(List<CartDetailsEntity> cartDetailsEntityList);




}
