package com.paymybuddy.transaction.configuration;

import com.paymybuddy.transaction.services.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Autowired
  JpaUserDetailsService jpaUserDetailsService;
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"))
        .authorizeRequests( auth -> auth
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/userform").permitAll()
            .antMatchers("/adduser").permitAll()
            .anyRequest().authenticated()
        )
        .userDetailsService(jpaUserDetailsService)
        .headers(headers -> headers.frameOptions().sameOrigin())
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .logout()
        .logoutUrl("/logout")
    ;
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}