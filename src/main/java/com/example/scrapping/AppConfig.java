package com.example.scrapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Helpers transferService() {
        return new Helpers();
    }
}
