CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    shipping_address_line_1 CHAR(255),
    shipping_address_line_2 CHAR(255),
    shipping_city CHAR(255),
    shipping_region CHAR(255),
    shipping_postal_code CHAR(255),
    shipping_country CHAR(255),
    created TIMESTAMP not null default current_timestamp,
    modified TIMESTAMP not null default current_timestamp
);

CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,
    product_id INTEGER,
    order_id INTEGER,
    quantity INTEGER,
    created timestamp not null default current_timestamp,
    modified timestamp not null default current_timestamp,
    CONSTRAINT fk_product_id FOREIGN KEY(product_id) REFERENCES products(id),
    CONSTRAINT fk_order_id FOREIGN KEY(order_id) REFERENCES orders(id)
);