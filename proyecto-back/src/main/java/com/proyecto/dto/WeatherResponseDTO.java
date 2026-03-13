package com.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de salida (Response).
 * Define los campos que el backend devuelve al frontend
 * después de procesar la consulta con n8n.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponseDTO {
    private String origin;               // Ciudad de origen
    private String destination;          // Ciudad de destino
    private String travelDate;           // Fecha del viaje
    private String transport;            // Medio de transporte

    private Double originTemperature;        // Temperatura en el origen (°C)
    private String originCondition;          // Condición climática en el origen

    private Double destinationTemperature;   // Temperatura en el destino (°C)
    private String destinationCondition;     // Condición climática en el destino

    private String recommendation;       // Reporte completo generado por el AI Agent (HTML)
}
