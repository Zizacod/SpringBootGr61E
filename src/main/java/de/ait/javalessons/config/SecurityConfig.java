package de.ait.javalessons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers("/public", "/api/public", "/h2-console/**").permitAll()
                        .requestMatchers("/api/private").hasRole("ADMIN")
                        .anyRequest().authenticated()).formLogin();

        http.headers().frameOptions().disable();
        http.csrf().disable();

        return http.build();
    }

    //InMemory
    /**@Bean
    public UserDetailsService userDetailsService() {
    UserDetails user = User.withUsername("user")
    .password(passwordEncoder().encode("password"))
    .roles("USER")
    .build();

    UserDetails admin = User.withUsername("admin")
    .password(passwordEncoder().encode("admin"))
    .roles("ADMIN")
    .build();
    return new InMemoryUserDetailsManager(user, admin);
    }*/

    @Bean
    public UserDetailsService users(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        return users;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}