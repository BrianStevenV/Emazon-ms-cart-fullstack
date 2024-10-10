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
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICartEntityMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    Cart toCart(CartEntity cartEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    CartEntity toCartEntity(Cart cart);

//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "userId", target = "userId")
//    @Mapping(source = "cartDetailEntities", target = "cartDetails")
//    Cart toCart(CartEntity cartEntity);
//
//    // Mapeo de Cart a CartEntity
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "userId", target = "userId")
//    @Mapping(target = "cartDetailEntities", expression = "java(mapCartDetails(cart.getCartDetails()))") // Mapeo personalizado para cartDetails
//    CartEntity toCartEntity(Cart cart);
//
//    // Método para mapear la lista de detalles del carrito (de CartDetails a CartDetailsEntity)
//    default List<CartDetailsEntity> mapCartDetails(List<CartDetails> cartDetails) {
//        return cartDetails.stream()
//                .map(this::toCartDetailsEntity) // Llama a un método de mapeo para CartDetailsEntity
//                .collect(Collectors.toList());
//    }
//
//    // Método para mapear CartDetailsEntity a CartDetails
//    CartDetails toCartDetails(CartDetailsEntity entity);
//
//    // Método para mapear CartDetails a CartDetailsEntity
//    CartDetailsEntity toCartDetailsEntity(CartDetails cartDetails);

}
