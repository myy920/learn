package com.myy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "myyAuditorAware")
public class JPAConfig {

    @Bean(name = "myyAuditorAware")
    public AuditorAware<String> myyAuditorAware() {
        return new MyyAuditorAware();
    }

    public static class MyyAuditorAware implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            return Optional.of("system");
        }
    }

}
