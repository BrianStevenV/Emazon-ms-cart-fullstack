package com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign;

import feign.Client;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.httpclient.ApacheHttpClient;

@Configuration
public class FeignClientConfig {

    @Bean
    public Client feignClient(){
        return new ApacheHttpClient();
    }

    @Bean
    public RequestInterceptor requestInterceptor(){
        return new JwtRequestInterceptor();
    }

    @Bean
    public ErrorDecoder errorDecoder(){
        return new CustomErrorDecoder();
    }
}
