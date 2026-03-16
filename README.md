
# 🌦️ WeatherGo — Asistente Inteligente de Viaje

WeatherGo es un asistente inteligente que consulta el clima en múltiples puntos de una ruta y genera recomendaciones de vestimenta personalizadas usando Inteligencia Artificial.

---

## 👥 Integrantes del Equipo
* **Laura Marcela Albarracin Serrano**
* **Joan Sebastian Jaimes Quintero**

---

## 🚀 ¿Qué hace?

El usuario ingresa su ciudad de origen, destino, fecha de viaje y medio de transporte. El sistema consulta el clima en **5 puntos de la ruta real**, analiza las condiciones meteorológicas y genera un reporte narrativo con recomendaciones de seguridad y vestimenta específicas según el transporte elegido (carro, moto o a pie).

---

## 🛠️ Tecnologías

| Capa | Tecnología |
|------|------------|
| **Frontend** | HTML5 + CSS3 + Vanilla JavaScript |
| **Backend** | Java 17 + Spring Boot 3.2.5 |
| **Automatización** | n8n (Self-hosted vía Docker) |
| **Inteligencia Artificial** | Google Gemini 1.5 Flash (AI Agent en n8n) |
| **Base de datos** | MySQL 8.0 (Contenedor local) |
| **MCP Server** | Node.js + Express |
| **APIs externas** | OpenRouteService (Rutas) + Open-Meteo (Clima) |

---

## 📂 Estructura del proyecto

```text
weatherassistant-n8n/
├── frontend/           → Interfaz web (HTML, CSS, JS)
├── proyecto-back/      → API REST en Spring Boot
├── mcp-server/         → Servidor MCP en Node.js (Herramientas IA)
├── database/           → Script SQL de inicialización
└── docs/               → Documentación técnica y diagramas

```

---

## 🔄 Flujo del sistema

```text
Usuario → Frontend → Spring Boot → Webhook n8n → Open-Meteo (5 puntos)
→ Google Gemini Flash → MySQL (Guardar historial) → Respuesta al usuario

```

---

## ⚙️ Instalación y configuración local

### Requisitos previos

* Java 17
* Node.js (v18 o superior)
* Docker activo (Para la base de datos y n8n)
* Cuenta en ngrok activa

### 1. Base de datos (MySQL)

Levanta un contenedor de MySQL en el puerto `3307` y crea las tablas ejecutando el script proporcionado:

```bash
# Entrar a MySQL y ejecutar el contenido de:
database/schema.sql

```

### 2. Backend (Java Spring Boot)

Configura tus credenciales y tu URL de ngrok en `proyecto-back/src/main/resources/application.properties`:

```properties
server.port=3001
spring.datasource.url=jdbc:mysql://127.0.0.1:3307/proyecto_n8n
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
n8n.webhook.url=[https://TU-URL-NGROK.ngrok-free.dev/webhook/clima-ruta](https://TU-URL-NGROK.ngrok-free.dev/webhook/clima-ruta)

```

Luego compila y ejecuta usando el wrapper de Maven:

```bash
cd proyecto-back
./mvnw clean install -DskipTests
./mvnw spring-boot:run

```

### 3. MCP Server (Node.js)

```bash
cd mcp-server
npm install
npm run dev

```

### 4. Automatización (n8n)

1. Importa el archivo del flujo `.json` en tu instancia local de n8n.
2. Configura las credenciales (Credentials) para:
* **OpenRouteService** (API Key)
* **Google Gemini** (API Key)
* **MySQL** (Conectado a `host.docker.internal:3307`)


3. Asegúrate de activar el flujo (Toggle en "Active") y guardar los cambios.

### 5. Frontend

Abre el archivo `frontend/index.html` utilizando la extensión **Live Server** en VS Code (se abrirá en el puerto 5500).

---

## 📡 Endpoints del backend

| Método | Ruta | Descripción |
| --- | --- | --- |
| `POST` | `/api/weather-outfit` | Envía los datos a n8n, consulta el clima y devuelve el reporte IA |
| `GET` | `/api/health` | Verifica que el servidor de Spring Boot esté vivo y escuchando |

---

## 📚 Documentación adicional

* [`docs/architecture.md`](https://www.google.com/search?q=docs/architecture.md) — Arquitectura detallada del sistema
* [`docs/api-spec.md`](https://www.google.com/search?q=docs/api-spec.md) — Especificación completa de la API
* [`docs/system-design.md`](https://www.google.com/search?q=docs/system-design.md) — Decisiones de diseño y escalabilidad
