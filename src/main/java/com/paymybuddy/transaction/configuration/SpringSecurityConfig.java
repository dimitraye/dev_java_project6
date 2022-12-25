package com.paymybuddy.transaction.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.assembler.AbstractMBeanInfoAssembler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("springuser").password(passwordEncoder().encode("spring123")).roles("USER");

    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // .antMatchers("/admin").hasRole("ADMIN")
                // .antMatchers("/user").hasRole("USER")
                //.antMatchers("/h2-console").hasRole("USER")
                //.anyRequest().permitAll()
                .antMatchers("/").permitAll().and()
                .authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and()
                .formLogin()
                .and()
                .oauth2Login()
        ;
        // TODO change config later
        // TODO see https://springframework.guru/using-the-h2-database-console-in-spring-boot-with-spring-security/
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
