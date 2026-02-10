package com.cts.ownerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class OwnerserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OwnerserviceApplication.class, args);
	}

}
