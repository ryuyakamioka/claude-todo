package com.example.todoapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI catTodoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("🐱 Cat Todo API")
                        .description("A cute cat-themed todo application API built with Spring Boot")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Claude Code Assistant")
                                .email("noreply@anthropic.com")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("Development server")));
    }
}