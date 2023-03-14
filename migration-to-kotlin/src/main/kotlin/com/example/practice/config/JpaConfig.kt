package com.example.practice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.Optional

@EnableJpaAuditing
@Configuration
class JpaConfig {
    @Bean
    fun auditorAware() : AuditorAware<String> {
        return AuditorAware { Optional.of("SEUNGSU") }
    }
}