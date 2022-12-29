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
        //Accès à H2
        .csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"))
        //Auhorisations d'accès aux requêtes
        .authorizeRequests( auth -> auth
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/userform").permitAll()
            .antMatchers("/adduser").permitAll()
            //Any other request need to be authentified
            .anyRequest().authenticated()
        )
        //Gestion de l'authentification de user JPA Spring security
        .userDetailsService(jpaUserDetailsService)
        //
        .headers(headers -> headers.frameOptions().sameOrigin())
        //Authentification par formulaire
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