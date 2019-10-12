package com.frcodes.administration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AdministrationApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(AdministrationApplication.class, args);
		System.out.println("Welcome!!!");
	}

}
