package com.PowerUpFullStack.ms_cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCartApplication.class, args);
	}

}
