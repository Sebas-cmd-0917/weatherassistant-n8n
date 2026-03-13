-- =============================================
-- Base de datos: weatherassistant
-- Asistente de vestimenta por clima
-- =============================================

-- Tabla: users
-- Guarda los usuarios del sistema (opcional, para futura autenticación)
CREATE TABLE IF NOT EXISTS users (
  id         SERIAL        PRIMARY KEY,
  name       VARCHAR(100)  NULL,
  email      VARCHAR(150)  NULL,
  created_at TIMESTAMP(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT uq_email UNIQUE (email)
);

-- Tabla: weather_queries
-- Guarda el historial de todas las consultas de clima realizadas
CREATE TABLE IF NOT EXISTS weather_queries (
  id                      SERIAL        PRIMARY KEY,
  origin                  VARCHAR(150)  NOT NULL,
  destination             VARCHAR(150)  NOT NULL,
  travel_date             DATE          NOT NULL,
  transport               VARCHAR(20)   NOT NULL,
  origin_temperature      DECIMAL(5,2)  NULL,
  destination_temperature DECIMAL(5,2)  NULL,
  origin_condition        VARCHAR(100)  NULL,
  destination_condition   VARCHAR(100)  NULL,
  recommended_clothes     TEXT          NOT NULL,
  user_id                 INT           NULL,
  created_at              TIMESTAMP(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_user FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE SET NULL
);
