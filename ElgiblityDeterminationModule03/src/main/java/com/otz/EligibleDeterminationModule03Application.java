package com.otz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EligibleDeterminationModule03Application {

	public static void main(String[] args) {
		SpringApplication.run(EligibleDeterminationModule03Application.class, args);
	}

}
