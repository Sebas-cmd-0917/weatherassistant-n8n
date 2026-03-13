package com.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Configuración del RestTemplate.
 *
 * RestTemplate es el cliente HTTP que usa el backend para llamar
 * al webhook de n8n. Sin configurar los timeouts, si n8n tarda
 * demasiado la conexión quedaría colgada indefinidamente.
 *
 * connectTimeout → tiempo máximo para establecer la conexión
 * readTimeout    → tiempo máximo para esperar la respuesta de n8n
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10_000);   // 10 segundos para conectar
        factory.setReadTimeout(180_000);     // 3 minutos para leer respuesta de n8n
        return new RestTemplate(factory);
    }
}
