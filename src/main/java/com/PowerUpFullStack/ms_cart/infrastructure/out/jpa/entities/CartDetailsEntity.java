package com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.utils.ConstantsEntity.CART_COLUMN_AMOUNT;
import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.utils.ConstantsEntity.CART_COLUMN_CART_ID;
import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.utils.ConstantsEntity.CART_COLUMN_CREATED_AT;
import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.utils.ConstantsEntity.CART_COLUMN_IS_ACTIVE;
import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.utils.ConstantsEntity.CART_COLUMN_PRODUCT_ID;
import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.utils.ConstantsEntity.CART_COLUMN_UPDATED_AT;
import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.utils.ConstantsEntity.CART_DETAILS_TABLE_NAME;
import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.utils.ConstantsEntity.COLUMN_DEFINITION_BOOLEAN_DEFAULT_TRUE;

@Entity
@Table(name = CART_DETAILS_TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = CART_COLUMN_AMOUNT, nullable = false)
    private Integer amount;

    @Column(name = CART_COLUMN_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = CART_COLUMN_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = CART_COLUMN_IS_ACTIVE, nullable = false, columnDefinition = COLUMN_DEFINITION_BOOLEAN_DEFAULT_TRUE)
    private Boolean isActive;

    @Column(name = CART_COLUMN_PRODUCT_ID, nullable = false)
    private Long productId;

//    @Column(name = CART_COLUMN_CART_ID, nullable = false)
//    private Long cartId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = CART_COLUMN_CART_ID, nullable = false)  // Define la columna para la FK
    private CartEntity cart;
}
