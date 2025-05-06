package com.sky.config;

import com.sky.Filter.JsonAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/css/**","/js/**","/img/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/login.html").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .addFilterAt(jsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.logout().logoutUrl("/logout")
                .logoutSuccessUrl("/login.html");
            return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public JsonAuthenticationFilter jsonAuthenticationFilter() throws Exception {
        return new JsonAuthenticationFilter(authenticationManager(null));
    }
}
