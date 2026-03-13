import express from "express";
import cors from "cors";
import { consultarClimaRuta } from "./tools/getWeather.js";
import { recommendOutfit } from "./tools/recommendOutfit.js";

/**
 * MCP Server — Model Context Protocol Server
 *
 * Este servidor expone "herramientas" (tools) que un agente de IA
 * como Claude puede invocar directamente. Es una capa de acceso
 * al sistema que no depende del frontend ni del backend Java.
 *
 * Corre en el puerto 4000.
 */
const app = express();
const PORT = 4000;

app.use(cors());
app.use(express.json());

// =============================================
// Tool: consultar_clima_ruta
// Llama al webhook de n8n con los datos del viaje
// y devuelve el reporte generado por el AI Agent
// =============================================
app.post("/tool/consultar_clima_ruta", async (req, res) => {
  const { origin, destination, travelDate, transport } = req.body;

  if (!origin || !destination || !travelDate || !transport) {
    return res.status(400).json({
      error: "Faltan campos: origin, destination, travelDate, transport"
    });
  }

  try {
    const result = await consultarClimaRuta({ origin, destination, travelDate, transport });
    res.json(result);
  } catch (error) {
    console.error("Error en consultar_clima_ruta:", error.message);
    res.status(500).json({ error: error.message });
  }
});

// =============================================
// Tool: recommend_outfit
// Fallback local — genera recomendación sin n8n
// Útil cuando n8n no está disponible
// =============================================
app.post("/tool/recommend_outfit", (req, res) => {
  const { temperature, condition, transport } = req.body;

  if (temperature === undefined) {
    return res.status(400).json({ error: "Falta el campo: temperature" });
  }

  const result = recommendOutfit({ temperature, condition, transport });
  res.json(result);
});

// =============================================
// Health check — verifica que el servidor esté activo
// =============================================
app.get("/health", (req, res) => {
  res.json({ status: "MCP Server running ✅", port: PORT });
});

// =============================================
// Lista de tools disponibles
// =============================================
app.get("/tools", (req, res) => {
  res.json({
    tools: [
      {
        name: "consultar_clima_ruta",
        description: "Consulta el clima de una ruta y genera recomendación de vestimenta usando n8n",
        endpoint: "POST /tool/consultar_clima_ruta",
        params: ["origin", "destination", "travelDate", "transport"]
      },
      {
        name: "recommend_outfit",
        description: "Genera recomendación de vestimenta por temperatura y condición (fallback sin n8n)",
        endpoint: "POST /tool/recommend_outfit",
        params: ["temperature", "condition", "transport"]
      }
    ]
  });
});

app.listen(PORT, () => {
  console.log(`\n🚀 MCP Server corriendo en http://localhost:${PORT}`);
  console.log(`📋 Tools disponibles en http://localhost:${PORT}/tools\n`);
});
