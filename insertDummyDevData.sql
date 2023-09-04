-- Script to insert dummy dev data into the database.

-- You first need to register two users into the system before running this scirpt.
DELETE FROM web_order_quantities;
DELETE FROM web_order;
DELETE FROM inventory;
DELETE FROM product;
DELETE FROM address;

INSERT INTO product (name, short_description, long_description, price) VALUES ('Product #1', 'Product one short description.', 'This is a very long description of product #1.', 5.50);
INSERT INTO product (name, short_description, long_description, price) VALUES ('Product #2', 'Product two short description.', 'This is a very long description of product #2.', 10.56);
INSERT INTO product (name, short_description, long_description, price) VALUES ('Product #3', 'Product three short description.', 'This is a very long description of product #3.', 2.74);
INSERT INTO product (name, short_description, long_description, price) VALUES ('Product #4', 'Product four short description.', 'This is a very long description of product #4.', 15.69);
INSERT INTO product (name, short_description, long_description, price) VALUES ('Product #5', 'Product five short description.', 'This is a very long description of product #5.', 42.59);

INSERT INTO inventory (product_id, quantity) VALUES (1, 5);
INSERT INTO inventory (product_id, quantity) VALUES (2, 8);
INSERT INTO inventory (product_id, quantity) VALUES (3, 12);
INSERT INTO inventory (product_id, quantity) VALUES (4, 73);
INSERT INTO inventory (product_id, quantity) VALUES (5, 2);

INSERT INTO address (address_line_1, city, country, user_id) VALUES ('123 Tester Hill', 'Testerton', 'England', 1);
INSERT INTO address (address_line_1, city, country, user_id) VALUES ('312 Spring Boot', 'Hibernate', 'England', 2);

INSERT INTO web_order (address_id, user_id) VALUES (1, 1);
INSERT INTO web_order (address_id, user_id) VALUES (1, 1);
INSERT INTO web_order (address_id, user_id) VALUES (1, 1);
INSERT INTO web_order (address_id, user_id) VALUES (2, 2);
INSERT INTO web_order (address_id, user_id) VALUES (2, 2);


INSERT INTO web_order_quantities (order_id, product_id, quantity) VALUES (1, 1, 5);
INSERT INTO web_order_quantities (order_id, product_id, quantity) VALUES (1, 2, 5);
INSERT INTO web_order_quantities (order_id, product_id, quantity) VALUES (2, 3, 5);
INSERT INTO web_order_quantities (order_id, product_id, quantity) VALUES (2, 2, 5);
INSERT INTO web_order_quantities (order_id, product_id, quantity) VALUES (2, 5, 5);
INSERT INTO web_order_quantities (order_id, product_id, quantity) VALUES (3, 3, 5);
INSERT INTO web_order_quantities (order_id, product_id, quantity) VALUES (4, 4, 5);
INSERT INTO web_order_quantities (order_id, product_id, quantity) VALUES (4, 2, 5);
INSERT INTO web_order_quantities (order_id, product_id, quantity) VALUES (5, 3, 5);
INSERT INTO web_order_quantities (order_id, product_id, quantity) VALUES (5, 1, 5);