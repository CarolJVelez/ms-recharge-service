

-- 1) Tabla users
CREATE TABLE IF NOT EXISTS users (
  user_id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255),
  last_name VARCHAR(255),
  document VARCHAR(255),
  phone VARCHAR(255),
  birth_date DATE,
  email VARCHAR(255),
  password_hash VARCHAR(255),
  active BOOLEAN DEFAULT true
);

-- Índices opcionales
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_document ON users(document);
