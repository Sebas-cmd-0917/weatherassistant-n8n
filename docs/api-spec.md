# 📡 Especificación de la API — ClimaVest

## ¿Qué es este documento?

Este documento describe todos los endpoints disponibles: qué reciben, qué devuelven y cómo usarlos.

---

## Backend — Spring Boot (puerto 3001)

### POST /api/weather-outfit
Recibe los datos del viaje y devuelve el reporte del AI Agent.

**Body:**
```json
{
  "origin": "Cúcuta",
  "destination": "Bogotá",
  "travelDate": "2026-03-15",
  "transport": "carro"
}
```

**Respuesta exitosa (200):**
```json
{
  "origin": "Cúcuta",
  "destination": "Bogotá",
  "travelDate": "2026-03-15",
  "transport": "carro",
  "recommendation": "¡Buenos días! 👋🚗..."
}
```

### GET /api/health
```
→ "Weather Assistant API running OK"
```

---

## MCP Server — Node.js (puerto 4000)

### POST /tool/consultar_clima_ruta
Llama al webhook de n8n con los datos del viaje.

### POST /tool/recommend_outfit
Fallback local sin n8n. Recibe `temperature`, `condition` y `transport`.

### GET /tools — Lista las tools disponibles
### GET /health — Verifica que el MCP Server esté activo

---

## Webhook de n8n

```
POST https://TU-URL-NGROK.ngrok-free.dev/webhook/clima-ruta
```

**Body:**
```json
{
  "Lugar de origen": "Cúcuta",
  "Destino": "Bogotá",
  "Dia de viaje": "2026-03-15",
  "Metodo de transporte": "Carro"
}
```

> ⚠️ Actualizar la URL en `application.properties` cada vez que cambie ngrok.
