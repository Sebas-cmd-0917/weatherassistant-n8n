package com.proyecto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa la tabla weather_queries en la base de datos.
 * Cada objeto de esta clase es un registro guardado en la tabla.
 * JPA/Hibernate usa esta clase para saber cómo mapear los datos.
 */
@Entity
@Table(name = "weather_queries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String origin;                  // Ciudad de origen

    @Column(nullable = false, length = 150)
    private String destination;             // Ciudad de destino

    @Column(name = "travel_date", nullable = false)
    private LocalDate travelDate;           // Fecha del viaje

    @Column(nullable = false, length = 20)
    private String transport;               // Medio de transporte

    @Column(name = "origin_temperature", precision = 5, scale = 2)
    private BigDecimal originTemperature;   // Temperatura en el origen

    @Column(name = "destination_temperature", precision = 5, scale = 2)
    private BigDecimal destinationTemperature; // Temperatura en el destino

    @Column(name = "origin_condition", length = 100)
    private String originCondition;         // Condición climática en el origen

    @Column(name = "destination_condition", length = 100)
    private String destinationCondition;    // Condición climática en el destino

    @Column(name = "recommended_clothes", nullable = false, columnDefinition = "TEXT")
    private String recommendedClothes;      // Reporte generado por el AI Agent

    @Column(name = "user_id")
    private Long userId;                    // Referencia opcional al usuario

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;        // Fecha y hora de la consulta

    // Se ejecuta automáticamente antes de guardar — asigna la fecha actual
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
