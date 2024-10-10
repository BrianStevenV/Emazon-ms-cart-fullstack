package com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.List;

import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.utils.ConstantsEntity.CART_COLUMN_CREATED_AT;
import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.utils.ConstantsEntity.CART_COLUMN_UPDATED_AT;
import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.utils.ConstantsEntity.CART_COLUMN_USER_ID;
import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.utils.ConstantsEntity.CART_DETAILS_ONE_TO_MANY_MAPPED_BY;
import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.utils.ConstantsEntity.CART_TABLE_NAME;

@Entity
@Table(name = CART_TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = CART_COLUMN_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = CART_COLUMN_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = CART_COLUMN_USER_ID, nullable = false)
    private Long userId;

    @OneToMany(mappedBy = CART_DETAILS_ONE_TO_MANY_MAPPED_BY, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartDetailsEntity> cartDetailEntities;
}
