package com.PowerUpFullStack.ms_cart.infrastructure.out.client.feing.port;

import com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.utils.ConstantsFeignClient.SUPPLY_BASE_HOST;
import static com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.utils.ConstantsFeignClient.SUPPLY_GET_NEXT_DATE_SUPPLY;
import static com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.utils.ConstantsFeignClient.SUPPLY_GET_NEXT_DATE_SUPPLY_PATH_VARIABLE_PRODUCT_ID;
import static com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.utils.ConstantsFeignClient.SUPPLY_SERVICE;

@FeignClient(name = SUPPLY_SERVICE, url = SUPPLY_BASE_HOST, configuration = FeignClientConfig.class)
public interface ISuppliesFeignClientExternalPort {

    @GetMapping(SUPPLY_GET_NEXT_DATE_SUPPLY)
    String getNextDateSupply(@PathVariable(SUPPLY_GET_NEXT_DATE_SUPPLY_PATH_VARIABLE_PRODUCT_ID) long productId);
}
