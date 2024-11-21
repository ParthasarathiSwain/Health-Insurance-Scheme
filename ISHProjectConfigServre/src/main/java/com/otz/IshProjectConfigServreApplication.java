package com.otz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class IshProjectConfigServreApplication {

	public static void main(String[] args) {
		SpringApplication.run(IshProjectConfigServreApplication.class, args);
	}

}
