package com.PowerUpFullStack.ms_cart.application.mapper;

import com.PowerUpFullStack.ms_cart.application.dto.request.OperationTypeRequestDto;
import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.application.dto.request.CartDetailsRequestDto;
import com.PowerUpFullStack.ms_cart.domain.model.OperationType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICartRequestMapper {
    Cart toCart (CartDetailsRequestDto cartDetailsRequestDto);
    OperationType toOperationType(OperationTypeRequestDto operationTypeRequestDto);
}
