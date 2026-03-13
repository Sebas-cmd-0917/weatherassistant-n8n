# 🏗️ Arquitectura del Sistema — ClimaVest

## ¿Qué es este documento?

Este documento describe cómo están organizados y conectados todos los componentes que forman el sistema **ClimaVest**, un asistente inteligente de viajes que consulta el clima de una ruta y genera recomendaciones de vestimenta personalizadas.

---

## Visión general

El sistema está compuesto por **5 capas principales** que trabajan juntas de forma coordinada:

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   Frontend  │────▶│  Backend    │────▶│    n8n      │────▶│  AI Agent   │────▶│  PostgreSQL │
│  (HTML/CSS/ │     │ (Java 17 /  │     │ (Automatiza │     │  (Google    │     │   (Neon)    │
│     JS)     │◀────│ Spring Boot)│◀────│  el flujo)  │◀────│   Gemini)   │     │             │
└─────────────┘     └─────────────┘     └─────────────┘     └─────────────┘     └─────────────┘
```

---

## Componentes detallados

### 1. Frontend (`/frontend`)
- **Tecnología:** HTML5, CSS3 y JavaScript puro (sin frameworks)
- **Puerto:** Se sirve como archivo estático en el navegador
- **Responsabilidad:** Mostrar el formulario al usuario y presentar el reporte generado por la IA
- **Comunicación:** Hace peticiones `POST` al backend en `http://localhost:3001/api/weather-outfit`
- **Timeout configurado:** 3 minutos (180,000 ms) para esperar la respuesta

### 2. Backend (`/proyecto-back`)
- **Tecnología:** Java 17 + Spring Boot 3.2.5 — Puerto: `3001`
- **Responsabilidad:** Recibir la solicitud del frontend, reformatear los datos y enviarlos al webhook de n8n

### 3. n8n (orquestador externo)
- **Tecnología:** n8n self-hosted expuesto via ngrok
- **Responsabilidad:** Geocodificación, cálculo de ruta, consulta de clima en 5 puntos, generación del reporte con IA e inserción en base de datos

### 4. MCP Server (`/mcp-server`)
- **Tecnología:** Node.js + Express — Puerto: `4000`
- **Responsabilidad:** Exponer herramientas para agentes de IA (Model Context Protocol)

### 5. Base de datos (`/database`)
- **Tecnología:** PostgreSQL en Neon (nube gratuita)
- **Quién inserta:** El nodo Postgres dentro del flujo de n8n

---

## Flujo completo de una consulta

```
Usuario llena el formulario
        │
        ▼
Frontend hace POST a Spring Boot :3001
        │
        ▼
Spring Boot reenvía al webhook de n8n
        │
        ▼
n8n geocodifica origen y destino (OpenRouteService)
        │
        ▼
n8n calcula la ruta según el transporte
        │
        ▼
n8n extrae 5 puntos y consulta Open-Meteo en cada uno
        │
        ▼
n8n envía datos al AI Agent (Google Gemini)
        │
        ▼
Gemini genera reporte HTML + bloque %%DATA%%
        │
        ▼
Nodo Code JS separa el reporte del bloque de datos
        │
        ▼
Nodo Postgres inserta en Neon
        │
        ▼
Respond to Webhook devuelve el reporte a Spring Boot
        │
        ▼
Spring Boot devuelve JSON al frontend
        │
        ▼
Frontend muestra el reporte al usuario ✅
```

---

## Decisiones técnicas importantes

**¿Por qué PostgreSQL en Neon y no MySQL local?**
n8n Cloud no puede conectarse a una base de datos local. Neon ofrece PostgreSQL gratuito accesible desde internet.

**¿Por qué el backend Java no guarda en base de datos?**
Para evitar duplicados. n8n ya guarda en Neon, por lo que el backend solo actúa como intermediario.

**¿Por qué 5 puntos de clima en la ruta?**
Consultar solo el origen o destino puede ser engañoso en rutas largas. Con 5 puntos se captura la variabilidad climática completa del trayecto.
