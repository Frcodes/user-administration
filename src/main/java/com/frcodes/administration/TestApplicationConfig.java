package com.frcodes.administration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.frcodes.administration.service.ConsoleService;

/**
 * Main class with the requirement annotations for Testing Spring boot
 * application. Define 'test' profile with the memory database H2.
 * 
 * @author frCodes
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = { "com.frcodes" })
@ComponentScan("com.frcodes")
@EnableTransactionManagement
public class TestApplicationConfig {

	@Autowired
	private ConsoleService consoleService;

	private static ConsoleService console;

	@PostConstruct
	private void initStaticDao() {
		console = this.consoleService;
	}

	public static void main(String[] args) {
		SpringApplication.run(TestApplicationConfig.class, args);
		console.start();
	}

	@Bean
	@Profile("test")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
		dataSource.setUsername("sa");
		dataSource.setPassword("sa");

		return dataSource;
	}

}
