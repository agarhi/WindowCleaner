package com.okd.demo.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter{
   @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
   @Override
   public AuthenticationManager authenticationManagerBean() throws Exception {
       return super.authenticationManagerBean();
   }
   
   @Bean
   public static NoOpPasswordEncoder passwordEncoder() {
    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
   }
}
