# 🧠 Diseño del Sistema — ClimaVest

## ¿Qué es este documento?

Explica las decisiones de diseño: por qué se eligió cada tecnología, cómo está diseñada la base de datos y consideraciones de escalabilidad.

---

## Decisiones de diseño

- **n8n:** Orquestador visual. Permite modificar el flujo sin tocar el código del backend.
- **Spring Boot:** Intermediario robusto con tipado fuerte y configuración de timeouts.
- **PostgreSQL en Neon:** n8n Cloud no puede conectarse a bases locales. Neon es gratuito y accesible desde internet.
- **Google Gemini:** API gratuita con excelente capacidad de seguir instrucciones HTML estrictas.
- **5 puntos de clima:** Captura la variabilidad climática en toda la ruta, no solo en origen/destino.

---

## Base de datos

```
users                     weather_queries
─────────────────         ──────────────────────────
id (PK)          ◀──┐     id (PK)
name                 └──  user_id (FK, nullable)
email                     origin, destination
created_at                travel_date, transport
                          origin_temperature
                          destination_temperature
                          origin_condition
                          destination_condition
                          recommended_clothes
                          created_at
```

---

## Flujo de n8n

```
Webhook → Nombrar variables → [Obtener origen + Obtener destino]
→ Unir coordenadas → Switch (Carro/Moto/A pie)
→ Obtener Ruta → Obtener 5 puntos → Loop → Consultar clima
→ Agrupar datos → Editar chatInput → AI Agent (Gemini)
→ Code JS → Postgres (Neon) → Respond to Webhook
```

---

## Escalabilidad futura

| Funcionalidad | Cómo implementarla |
|---------------|-------------------|
| Autenticación | Activar tabla `users` + Spring Security |
| Historial personal | Filtrar por `user_id` |
| Predicciones futuras | Usar parámetro `hourly` en Open-Meteo |
| App móvil | El backend ya es API REST |
| Notificaciones | Expandir nodo Telegram en n8n |
