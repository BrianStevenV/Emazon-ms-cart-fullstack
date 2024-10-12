package com.PowerUpFullStack.ms_cart.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponseDto {
    private long id;
    private String name;
    private Integer amountInStock;
    private Double price;
    private Integer amountInCart;
    private String brandName;
    private List<String> categoryNames;
    private String nextSupplDate;
    private long cartDetailsId;

    public ProductResponseDto(String nextSupplDate) {
        this.nextSupplDate = nextSupplDate;
    }
}
