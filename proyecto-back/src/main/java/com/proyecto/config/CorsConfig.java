package com.proyecto.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de CORS (Cross-Origin Resource Sharing).
 *
 * Sin esta configuración, el navegador bloquearía las peticiones
 * del frontend hacia el backend porque están en puertos diferentes
 * (frontend en 5500, backend en 3001). CORS le dice al backend
 * que acepte peticiones de esos orígenes.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                    "http://localhost:5500",
                    "http://127.0.0.1:5500",
                    "http://localhost:3000"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
