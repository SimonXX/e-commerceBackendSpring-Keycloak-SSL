-- Creazione della tabella "users"
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    telephone_number VARCHAR(20),
    email VARCHAR(90),
    address VARCHAR(150)
);

CREATE TABLE products (
  id SERIAL PRIMARY KEY,
  bar_code VARCHAR(70),
  name VARCHAR(50),
  description VARCHAR(500),
  image VARCHAR(500),
  price DOUBLE PRECISION,
  quantity INTEGER,
  version BIGINT NOT NULL
);

CREATE TABLE purchase (
  id SERIAL PRIMARY KEY,
  purchase_time TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
  buyer_id INTEGER REFERENCES users(id)
);

CREATE TABLE product_in_purchase (
  id SERIAL PRIMARY KEY,
  related_purchase_id INTEGER REFERENCES purchase(id),
  quantity INTEGER,
  product_id INTEGER REFERENCES products(id)
);

CREATE TABLE review (
  id SERIAL PRIMARY KEY,
  user_id INTEGER REFERENCES users(id),
  product_id INTEGER REFERENCES products(id),
  comment TEXT,
  rating INT
);

CREATE TABLE favorite (
  id SERIAL PRIMARY KEY,
  user_id INTEGER REFERENCES users(id),
  product_id INTEGER REFERENCES products(id)
);




