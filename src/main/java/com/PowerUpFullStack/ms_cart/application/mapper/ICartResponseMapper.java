package com.PowerUpFullStack.ms_cart.application.mapper;

import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.application.dto.response.CartResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICartResponseMapper {
    CartResponseDto toCartResponseDto(Cart cart);
}
