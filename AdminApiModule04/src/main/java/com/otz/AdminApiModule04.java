package com.otz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AdminApiModule04 {

	public static void main(String[] args) {
		SpringApplication.run(AdminApiModule04.class, args);
	}

}
