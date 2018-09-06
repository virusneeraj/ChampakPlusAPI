package com.champak.plus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
// http://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html
// Switch off the Spring Boot security configuration
//@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomAuthenticationProvider customAuthProvider;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/**", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/security/**", "/","/user/","/codes/**").permitAll()
                .antMatchers("/mongo/**").permitAll()
                .antMatchers("/ecom/**").permitAll()
                .antMatchers("/contest/**").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/service/**","/email/**").hasAnyRole("USER","ADMIN")
                .anyRequest().authenticated()
                //.and()
                //.formLogin()
                //.loginPage("/login")
                //.successHandler(customSuccessHandler)
                //.permitAll()
                .and()
                .logout()
                .permitAll()
                //.and()
                //.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and().httpBasic()
        ;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.authenticationProvider(customAuthProvider);
        auth.inMemoryAuthentication()
                .withUser("jhane")
                .password("pass")
                .roles("USER");
    }



    /*
    //Spring Boot configured this already.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }*/

}
