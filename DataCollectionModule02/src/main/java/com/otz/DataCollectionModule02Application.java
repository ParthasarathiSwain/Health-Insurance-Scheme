package com.otz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DataCollectionModule02Application {

	public static void main(String[] args) {
		SpringApplication.run(DataCollectionModule02Application.class, args);
	}

}
