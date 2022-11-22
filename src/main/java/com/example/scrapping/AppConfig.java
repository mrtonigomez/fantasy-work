package com.example.scrapping;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Helpers helper() {
        return new Helpers();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
