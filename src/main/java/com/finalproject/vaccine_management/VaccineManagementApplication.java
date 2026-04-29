package com.finalproject.vaccine_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class VaccineManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaccineManagementApplication.class, args);
	}

}
