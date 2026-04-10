package com.idat.campeonato_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permite el envío de credenciales/cookies
        config.setAllowCredentials(true);

        // Las URLs exactas permitidas
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedOrigin("https://campeonato-theta.vercel.app");

        // Permite cualquier cabecera y cualquier método (GET, POST, OPTIONS, etc.)
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        // Aplica esta regla a TODAS las rutas de tu API
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}