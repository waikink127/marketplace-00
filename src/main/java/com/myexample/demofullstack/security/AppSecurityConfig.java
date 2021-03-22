package com.myexample.demofullstack.security;

import com.myexample.demofullstack.jwt.JwtUsernamePasswordAuthFilter;
import com.myexample.demofullstack.jwt.JwtVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppUserDetailsService appUserDetailsService;
    @Autowired
    private BCryptPasswordEncoder encoder;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JwtUsernamePasswordAuthFilter jwtUsernamePasswordAuthFilter = new JwtUsernamePasswordAuthFilter(authenticationManager());

        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(HttpMethod.POST,"/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(jwtUsernamePasswordAuthFilter)
                .addFilterBefore(jwtVerifier(), UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetailsService).passwordEncoder(encoder);
    }


    @Bean
    public JwtVerifier jwtVerifier() {
        return new JwtVerifier();
    }

}
