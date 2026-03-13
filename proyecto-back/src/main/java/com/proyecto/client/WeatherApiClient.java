package com.proyecto.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Cliente HTTP hacia n8n.
 *
 * Encapsula la llamada al webhook de n8n en un componente reutilizable.
 * Aunque el WeatherService llama directamente a n8n, este cliente
 * existe como capa de abstracción — si en el futuro se cambia la URL
 * o la forma de llamar a n8n, solo se modifica aquí.
 */
@Component
public class WeatherApiClient {

    private final RestTemplate restTemplate;

    @Value("${n8n.webhook.url}")
    private String n8nWebhookUrl;

    public WeatherApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> callN8nWebhook(Map<String, String> body) {
        return restTemplate.postForObject(n8nWebhookUrl, body, Map.class);
    }
}
