package com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.mapper;

import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.CartDetailsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICartDetailsEntityMapper {
    @Mapping(target = "active", source = "isActive")  // Cambiado a 'active'
    @Mapping(target = "cart", source = "cart")
    CartDetails toCartDetails(CartDetailsEntity cartDetailsEntity);

    @Mapping(target = "cart", source = "cart")
    @Mapping(target = "isActive", source = "active")  // Cambiado a 'active'
    CartDetailsEntity toCartDetailsEntity(CartDetails cartDetails);



}
