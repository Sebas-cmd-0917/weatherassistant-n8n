const API_URL = "http://localhost:3001/api/weather-outfit";

let selectedTransport = null;

function selectTransport(btn) {
  document.querySelectorAll(".transport-btn").forEach(b => b.classList.remove("selected"));
  btn.classList.add("selected");
  selectedTransport = btn.dataset.value;
}

async function consultWeather() {
  const origin      = document.getElementById("originInput").value.trim();
  const destination = document.getElementById("destinationInput").value.trim();
  const travelDate  = document.getElementById("dateInput").value;
  const errorMsg    = document.getElementById("errorMsg");

  if (!origin || !destination || !travelDate || !selectedTransport) {
    errorMsg.textContent = "⚠️ Completa todos los campos y selecciona un medio de transporte.";
    errorMsg.classList.remove("hidden");
    return;
  }
  errorMsg.classList.add("hidden");

  showLoading(true);
  hideResult();

  try {
    const controller = new AbortController();
    const timeoutId = setTimeout(() => controller.abort(), 180_000); // 3 minutos

    const response = await fetch(API_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ origin, destination, travelDate, transport: selectedTransport }),
      signal: controller.signal
    });

    clearTimeout(timeoutId);

    if (!response.ok) {
      const err = await response.text();
      console.error("Error del servidor:", err);
      throw new Error("Error al procesar la solicitud.");
    }

    const data = await response.json();
    showResult(data);

  } catch (error) {
    console.error(error);
    if (error.name === "AbortError") {
      errorMsg.textContent = "⚠️ La consulta tardó demasiado. Verifica que n8n esté activo.";
    } else {
      errorMsg.textContent = "⚠️ " + (error.message || "Error al consultar el clima.");
    }
    errorMsg.classList.remove("hidden");
  } finally {
    showLoading(false);
  }
}

function formatRecommendation(text) {
  if (!text) return "";
  return text
    .replace(/\*\*(.*?)\*\*/g, "<b>$1</b>")
    .replace(/\*(.*?)\*/g, "<b>$1</b>")
    .replace(/\n/g, "<br>");
}

function showResult(data) {
  document.getElementById("originBadge").textContent   = "📍 " + data.origin;
  document.getElementById("destBadge").textContent     = "🏁 " + data.destination;
  document.getElementById("dateBadge").textContent     = "📅 " + formatDate(data.travelDate);
  document.getElementById("transportBadge").textContent = iconTransport(data.transport) + " " + data.transport;

  document.getElementById("recommendation").innerHTML = formatRecommendation(data.recommendation);
  document.getElementById("result").classList.remove("hidden");
}

function hideResult() {
  document.getElementById("result").classList.add("hidden");
}

function showLoading(show) {
  const loader = document.getElementById("loading");
  const text   = document.getElementById("loadingText");
  if (show) {
    loader.classList.remove("hidden");
    const msgs = [
      "Analizando tu ruta...",
      "Calculando puntos de clima...",
      "Consultando condiciones del tiempo...",
      "Generando tu reporte con IA..."
    ];
    let i = 0;
    window._loadingInterval = setInterval(() => {
      if (text) text.textContent = msgs[i % msgs.length];
      i++;
    }, 3000);
  } else {
    loader.classList.add("hidden");
    if (window._loadingInterval) clearInterval(window._loadingInterval);
  }
}

function resetForm() {
  hideResult();
  document.getElementById("originInput").value = "";
  document.getElementById("destinationInput").value = "";
  document.getElementById("dateInput").value = "";
  document.querySelectorAll(".transport-btn").forEach(b => b.classList.remove("selected"));
  selectedTransport = null;
  document.getElementById("errorMsg").classList.add("hidden");
  document.getElementById("originInput").focus();
}

function formatDate(dateStr) {
  const [year, month, day] = dateStr.split("-");
  const months = ["Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"];
  return `${day} ${months[parseInt(month)-1]} ${year}`;
}

function iconTransport(transport) {
  if (!transport) return "";
  const t = transport.toLowerCase();
  if (t.includes("moto")) return "🏍️";
  if (t.includes("carro") || t.includes("coche")) return "🚗";
  if (t.includes("pie")) return "🚶";
  return "🚌";
}

document.addEventListener("DOMContentLoaded", () => {
  ["originInput", "destinationInput", "dateInput"].forEach(id => {
    document.getElementById(id).addEventListener("keydown", e => {
      if (e.key === "Enter") consultWeather();
    });
  });
});
