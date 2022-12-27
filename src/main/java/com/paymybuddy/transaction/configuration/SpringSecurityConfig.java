package com.paymybuddy.transaction.configuration;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*@Configuration
@EnableWebSecurity*/
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
/*
    class LoginPageFilter extends GenericFilterBean {

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
            IOException, ServletException {
            if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && ((HttpServletRequest)request).getRequestURI().equals("/login")) {
                System.out.println("user is authenticated but trying to access login page, redirecting to /");
                ((HttpServletResponse)response).sendRedirect("/");
            }
            chain.doFilter(request, response);
        }

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("springuser").password(passwordEncoder().encode("spring123")).roles("USER");

    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(
            new LoginPageFilter(), DefaultLoginPageGeneratingFilter.class);

        http.authorizeRequests()
            .antMatchers("/").permitAll().and()
            .authorizeRequests().antMatchers("/h2-console/**").permitAll()
            .and().authorizeRequests().antMatchers("/login").not().authenticated()
            .and()
            .oauth2Login()
            .and()
            .logout()
            .logoutUrl("/logout")
        ;
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private OidcIdToken getIdToken(OAuth2User principal){
        if(principal instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser)principal;
            return oidcUser.getIdToken();
        }
        return null;
    }*/
}


