package com.proyecto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Prueba de arranque de la aplicación.
 *
 * Verifica que el contexto de Spring Boot se cargue correctamente,
 * es decir, que todos los beans, configuraciones y dependencias
 * estén bien definidos. Si algo está mal configurado, este test falla.
 */
@SpringBootTest
class WeatherAssistantApplicationTests {

    @Test
    void contextLoads() {
        // Si el contexto de Spring carga sin errores, el test pasa
    }
}
