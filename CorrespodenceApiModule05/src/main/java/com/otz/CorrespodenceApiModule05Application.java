package com.otz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CorrespodenceApiModule05Application {

	public static void main(String[] args) {
		SpringApplication.run(CorrespodenceApiModule05Application.class, args);
	}

}
