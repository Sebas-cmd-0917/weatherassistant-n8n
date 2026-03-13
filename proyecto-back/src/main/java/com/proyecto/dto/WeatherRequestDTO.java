package com.proyecto.dto;

import lombok.Data;

/**
 * DTO de entrada (Request).
 * Define los campos que el frontend envía al backend
 * cuando el usuario hace una consulta de clima.
 */
@Data
public class WeatherRequestDTO {
    private String origin;       // Ciudad de origen del viaje
    private String destination;  // Ciudad de destino del viaje
    private String travelDate;   // Fecha del viaje (formato: yyyy-MM-dd)
    private String transport;    // Medio de transporte: carro, moto, a pie
}
