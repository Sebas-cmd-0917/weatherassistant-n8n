package com.proyecto.repository;

import com.proyecto.model.WeatherQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de acceso a datos para WeatherQuery.
 * Al extender JpaRepository, Spring genera automáticamente
 * los métodos básicos: save(), findById(), findAll(), delete(), etc.
 *
 * Los métodos adicionales usan convención de nombres de Spring Data JPA
 * para generar el SQL automáticamente sin escribir queries.
 */
@Repository
public interface WeatherQueryRepository extends JpaRepository<WeatherQuery, Long> {

    // Busca todas las consultas de un origen específico, ordenadas por fecha descendente
    List<WeatherQuery> findByOriginIgnoreCaseOrderByCreatedAtDesc(String origin);

    // Busca todas las consultas de un destino específico, ordenadas por fecha descendente
    List<WeatherQuery> findByDestinationIgnoreCaseOrderByCreatedAtDesc(String destination);
}
