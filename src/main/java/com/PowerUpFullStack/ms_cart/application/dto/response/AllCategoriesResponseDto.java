package com.PowerUpFullStack.ms_cart.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllCategoriesResponseDto {
    Map<Long, List<Long>> categories;
}
