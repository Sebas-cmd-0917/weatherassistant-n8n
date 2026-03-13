/**
 * Tool: recommend_outfit
 *
 * Genera una recomendación de vestimenta basada en temperatura
 * y condición climática de forma local, sin necesidad de n8n ni IA.
 *
 * Se usa como FALLBACK cuando n8n no está disponible.
 * En el flujo principal, esta lógica la maneja Google Gemini
 * con mucho mayor detalle y personalidad.
 */
export function recommendOutfit({ temperature, condition, transport }) {
  let recommendation = "";

  // Recomendación base por temperatura
  if (temperature < 10) {
    recommendation = "Usa abrigo grueso, bufanda y pantalón largo.";
  } else if (temperature < 18) {
    recommendation = "Usa chaqueta ligera o suéter.";
  } else if (temperature < 26) {
    recommendation = "Usa ropa cómoda de media estación.";
  } else {
    recommendation = "Usa ropa ligera y fresca.";
  }

  // Ajuste por condición climática
  const cond = (condition || "").toLowerCase();
  if (cond.includes("rain") || cond.includes("lluvia") || cond.includes("drizzle")) {
    recommendation += " Lleva paraguas o impermeable.";
  }
  if (cond.includes("wind") || cond.includes("viento")) {
    recommendation += " Lleva chaqueta cortavientos.";
  }
  if (cond.includes("snow") || cond.includes("nieve")) {
    recommendation += " Usa botas impermeables y ropa térmica.";
  }

  // Ajuste por medio de transporte
  const trans = (transport || "").toLowerCase();
  if (trans.includes("moto")) {
    recommendation += " En moto: usa casco, chaqueta con protecciones y guantes.";
  } else if (trans.includes("pie")) {
    recommendation += " A pie: usa calzado cómodo y ropa transpirable.";
  } else if (trans.includes("carro") || trans.includes("coche")) {
    recommendation += " En carro: lleva una capa extra por el aire acondicionado.";
  }

  return { recommendation };
}
