package com.okd.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import com.okd.demo.oauth.CustomUserDetailsService;

@SpringBootApplication
@Configuration
@EnableCaching
@ComponentScan(basePackages= {"com.okd"})
@EntityScan("com.okd.entity")
public class HelloWorldApplication {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldApplication.class, args);
	}
	
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
	    builder.userDetailsService(userDetailsService);
	}
	
}
