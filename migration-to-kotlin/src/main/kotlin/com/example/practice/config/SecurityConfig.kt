package com.example.practice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
class SecurityConfig {
    // Before) WebSecurityConfigurerAdapter의 configure 메서드를 오버라이딩 하는 방식
    // After) SecurityFilterChain <--- Bean 등록하는 방식
    @Bean
    fun securityFilterChain(http: HttpSecurity) : SecurityFilterChain {
        return http.csrf().disable()
            .authorizeHttpRequests { it.anyRequest().permitAll() }
            .formLogin()
            .and()
            .build()
    }
}
