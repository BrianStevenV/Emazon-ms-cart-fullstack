package com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.mapper;

import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICartEntityMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "cartDetailEntities", target = "cartDetails") // Mapeo de la lista
    Cart toCart(CartEntity cartEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "cartDetails", target = "cartDetailEntities") // Mapeo inverso
    CartEntity toCartEntity(Cart cart);

}
