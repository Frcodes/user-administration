package com.frcodes.administration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.frcodes.administration.service.ConsoleService;

/**
 * Main class with the requirement annotations for Spring boot application
 * 
 * @author Asus
 *
 */
@SpringBootApplication
@ComponentScan("com.frcodes")
@EnableJpaRepositories("com.frcodes")
@EntityScan("com.frcodes")
public class Application {

	@Autowired
	private ConsoleService consoleService;

	private static ConsoleService console;

	@PostConstruct
	private void initStaticDao() {
		console = this.consoleService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		console.start();
	}

}
