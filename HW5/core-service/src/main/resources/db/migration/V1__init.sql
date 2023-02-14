CREATE TABLE products (
    id bigserial  PRIMARY KEY,
    title         VARCHAR(255),
    price         INT,
    created_at    timestamp default current_timestamp,
    updated_at    timestamp default current_timestamp
                      );

INSERT INTO products (title, price)
VALUES ('Tomato', 10),('Apple', 20),('Cucumber', 30),('Bread', 40),('Milk', 50),('Carrot', 60),('Pineapple', 70),('Strawberry', 80),('Salt', 90),('Sugar', 100);

create table users (
     id bigserial  PRIMARY KEY,
     username      varchar(30) not null unique,
     password      varchar(80) not null,
     email         varchar(50) unique,
     created_at    timestamp default current_timestamp,
     updated_at    timestamp default current_timestamp
                   );

create table roles (
     id bigserial  PRIMARY KEY,
     name          varchar(50) not null,
     created_at    timestamp default current_timestamp,
     updated_at    timestamp default current_timestamp
                   );

CREATE TABLE users_roles (
      user_id      bigint not null,
      role_id      bigint not null,
      primary key  (user_id, role_id),
      foreign key  (user_id) references users (id),
      foreign key  (role_id) references roles (id)
);

insert into roles (name)
values ('ROLE_USER'), ('ROLE_MANAGER');

insert into users (username, password, email)
values ('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@geekbrains.ru'),
       ('manager', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'manager@geekbrains.ru');

insert into users_roles (user_id, role_id)
values
    (1, 1),
    (2, 1),
    (2, 2);

create table orders
(
    id          bigserial primary key,
    user_id     bigint not null references users (id),
    total_price int    not null,
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
    price_per_product int    not null,
    price             int    not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);

insert into orders (user_id, total_price, address, phone)
values (1, 200, 'address', '89161234567');

insert into order_items (product_id, order_id, quantity, price_per_product, price)
values (1, 1, 2, 100, 200);
