package com.frcodes.administration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 

	    // Secure the endpoins with HTTP Basic authentication
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {

	    	http
            .authorizeRequests()
                .antMatchers("/", "/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/admin/health")
                .permitAll()
                .and()
            .logout()
                .permitAll();
	    }
	    
	   
	
}