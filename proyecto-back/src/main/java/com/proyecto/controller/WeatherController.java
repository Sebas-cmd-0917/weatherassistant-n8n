package com.proyecto.controller;

import com.proyecto.dto.WeatherRequestDTO;
import com.proyecto.dto.WeatherResponseDTO;
import com.proyecto.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST de la API.
 *
 * Es el punto de entrada de todas las peticiones HTTP.
 * Recibe el JSON del frontend, lo pasa al servicio para procesarlo
 * y devuelve la respuesta. No contiene lógica de negocio — su
 * única responsabilidad es recibir y responder peticiones.
 */
@RestController
@RequestMapping("/api")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Endpoint principal: recibe los datos del viaje y devuelve el reporte de clima.
     * Ruta: POST http://localhost:3001/api/weather-outfit
     */
    @PostMapping("/weather-outfit")
    public ResponseEntity<WeatherResponseDTO> getWeatherOutfit(@RequestBody WeatherRequestDTO request) {
        WeatherResponseDTO response = weatherService.getWeatherAndRecommendation(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint de salud: verifica que el servidor esté corriendo.
     * Ruta: GET http://localhost:3001/api/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Weather Assistant API running OK");
    }
}
