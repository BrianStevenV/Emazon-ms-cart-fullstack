package com.PowerUpFullStack.ms_cart.domain.model;

public class Available {
    Boolean available;

    public Available(Boolean available) {
        this.available = available;
    }

    public Available(){}

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
