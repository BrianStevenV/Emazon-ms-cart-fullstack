package com.PowerUpFullStack.ms_cart.application.mapper;

import com.PowerUpFullStack.ms_cart.application.dto.request.CartDetailsRequestDto;
import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICartDetailsRequestMapper {
    CartDetails toCartDetails (CartDetailsRequestDto cartDetailsRequestDto);
}
