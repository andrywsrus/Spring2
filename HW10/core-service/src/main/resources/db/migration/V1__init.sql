CREATE TABLE products (
    id bigserial  PRIMARY KEY,
    title         VARCHAR(255),
    price         numeric(9, 2) not null,
    created_at    timestamp default current_timestamp,
    updated_at    timestamp default current_timestamp
                      );

INSERT INTO products (title, price)
VALUES ('Tomato', 10),('Apple', 20),('Cucumber', 30),('Bread', 40),('Milk', 50),('Carrot', 60),('Pineapple', 70),('Strawberry', 80),('Salt', 90),('Sugar', 100);

create table orders
(
    id          bigserial primary key,
    username    varchar(255) not null,
    total_price numeric(9, 2) not null,
    address     varchar(255),
    phone       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table order_items
(
    id                bigserial primary key,
    product_id        bigint not null references products (id),
    order_id          bigint not null references orders (id),
    quantity          int    not null,
    price_per_product numeric(9, 2) not null,
    price             numeric(9, 2) not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);

insert into orders (username, total_price, address, phone)
values ('user', 200.00, 'address', '12345');

insert into order_items (product_id, order_id, quantity, price_per_product, price)
values (1, 1, 2, 100.00, 200.00);
