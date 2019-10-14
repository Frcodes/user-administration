package com.frcodes.administration;

import java.util.Arrays;
import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
	
	@Bean
	public CorsFilter corsFilter() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    final CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.setAllowedOrigins(Collections.singletonList("*"));
	    config.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Origin", "Content-Type", "Accept"));
	    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
	    source.registerCorsConfiguration("/**", config);
	    return new CorsFilter(source);
	}

}
