package com.ssafy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class TriendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TriendApplication.class, args);
	}

}
