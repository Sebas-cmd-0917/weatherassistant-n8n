package com.proyecto.util;

/**
 * Utilidad de recomendación de vestimenta.
 *
 * Provee lógica local de recomendación basada en temperatura
 * y condición climática. Se usa como FALLBACK cuando n8n no
 * está disponible o como respaldo del MCP Server.
 *
 * En el flujo principal, esta lógica la maneja el AI Agent
 * de Google Gemini dentro de n8n con mucho mayor detalle.
 */
public class ClothingRecommendationUtil {

    /**
     * Genera una recomendación de vestimenta según temperatura, condición y transporte.
     *
     * @param temperature  temperatura en grados Celsius
     * @param condition    condición climática (lluvia, viento, nieve, etc.)
     * @param transport    medio de transporte (carro, moto, a pie)
     * @return recomendación de vestimenta como texto
     */
    public static String recommend(double temperature, String condition, String transport) {
        StringBuilder recommendation = new StringBuilder();

        // Recomendación base por temperatura
        if (temperature < 10) {
            recommendation.append("Usa abrigo grueso, bufanda y pantalón largo.");
        } else if (temperature < 18) {
            recommendation.append("Usa chaqueta ligera o suéter.");
        } else if (temperature < 26) {
            recommendation.append("Usa ropa cómoda de media estación.");
        } else {
            recommendation.append("Usa ropa ligera y fresca.");
        }

        // Ajuste por condición climática
        String cond = (condition != null) ? condition.toLowerCase() : "";
        if (cond.contains("rain") || cond.contains("lluvia") || cond.contains("drizzle")) {
            recommendation.append(" Lleva paraguas o impermeable.");
        }
        if (cond.contains("wind") || cond.contains("viento")) {
            recommendation.append(" Lleva chaqueta cortavientos.");
        }
        if (cond.contains("snow") || cond.contains("nieve")) {
            recommendation.append(" Usa botas impermeables y ropa térmica.");
        }

        // Ajuste por medio de transporte
        String trans = (transport != null) ? transport.toLowerCase() : "";
        if (trans.contains("moto")) {
            recommendation.append(" En moto: usa casco, chaqueta con protecciones y guantes.");
        } else if (trans.contains("pie")) {
            recommendation.append(" A pie: usa calzado cómodo y ropa transpirable.");
        } else if (trans.contains("carro") || trans.contains("coche")) {
            recommendation.append(" En carro: lleva una capa extra por el aire acondicionado.");
        }

        return recommendation.toString();
    }
}
