# 🌦️ WeatherGo — Asistente Inteligente de Viaje

WeatherGo es un asistente inteligente que consulta el clima en múltiples puntos de una ruta y genera recomendaciones de vestimenta personalizadas usando inteligencia artificial.

---

## ¿Qué hace?

El usuario ingresa su ciudad de origen, destino, fecha de viaje y medio de transporte. El sistema consulta el clima en **5 puntos de la ruta real**, analiza las condiciones meteorológicas y genera un reporte narrativo con recomendaciones específicas según el transporte elegido (carro, moto o a pie).

---

## Tecnologías

| Capa | Tecnología |
|------|------------|
| Frontend | HTML5 + CSS3 + JavaScript |
| Backend | Java 17 + Spring Boot 3.2.5 |
| Automatización | n8n (self-hosted) |
| Inteligencia Artificial | Google Gemini (AI Agent en n8n) |
| Base de datos | PostgreSQL en Neon (nube) |
| MCP Server | Node.js + Express |
| APIs externas | OpenRouteService + Open-Meteo |

---

## Estructura del proyecto

```
weatherassistant-n8n/
├── frontend/           → Interfaz web (HTML, CSS, JS)
├── proyecto-back/      → API REST en Spring Boot
├── mcp-server/         → Servidor MCP en Node.js
├── database/           → Script SQL de la base de datos
└── docs/               → Documentación técnica
```

---

## Flujo del sistema

```
Usuario → Frontend → Spring Boot → n8n → Open-Meteo (5 puntos)
→ Google Gemini → PostgreSQL (Neon) → Respuesta al usuario
```

---

## Instalación y configuración

### 1. Base de datos
Crear las tablas en Neon ejecutando el script:
```
database/schema.sql
```

### 2. Backend Java
Configurar `proyecto-back/src/main/resources/application.properties`:
```properties
server.port=3001
n8n.webhook.url=https://TU-URL-NGROK.ngrok-free.dev/webhook/clima-ruta
```
Luego compilar y ejecutar:
```bash
cd proyecto-back
mvn spring-boot:run
```

### 3. MCP Server
```bash
cd mcp-server
npm install
npm run dev
```

### 4. Frontend
Abrir `frontend/index.html` con Live Server en VS Code (puerto 5500).

### 5. n8n
Importar el flujo de n8n y configurar las credenciales de:
- OpenRouteService (API key)
- Google Gemini (API key)
- PostgreSQL (cadena de conexión de Neon)

---

## Endpoints del backend

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/weather-outfit` | Consulta clima y genera reporte |
| GET | `/api/health` | Verifica que el servidor esté activo |

---

## Documentación

- [`docs/architecture.md`](docs/architecture.md) — Arquitectura del sistema
- [`docs/api-spec.md`](docs/api-spec.md) — Especificación de la API
- [`docs/system-design.md`](docs/system-design.md) — Diseño y decisiones técnicas
