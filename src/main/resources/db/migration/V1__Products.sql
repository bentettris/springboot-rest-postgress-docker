CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    description CHAR(255),
    name CHAR(255),
    price CHAR(50),
    sku CHAR(50),
    created timestamp not null default current_timestamp,
    modified timestamp not null default current_timestamp
);