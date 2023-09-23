DROP TABLE IF EXISTS local_user;
CREATE TABLE local_user (
  id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  email varchar(320) NOT NULL,
  email_verified bit NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  password varchar(1000) NOT NULL,
  username varchar(255) NOT NULL UNIQUE
);

--
-- Table structure for table `product`
--
DROP TABLE IF EXISTS product;

CREATE TABLE product (
  id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
  long_description varchar(255) DEFAULT NULL,
  name varchar(255) NOT NULL UNIQUE,
  price double NOT NULL,
  short_description varchar(255) NOT NULL
);

DROP TABLE IF EXISTS address;

CREATE TABLE address (
  id bigint NOT NULL AUTO_INCREMENT,
  address_line_1 varchar(512) NOT NULL,
  address_line_2 varchar(512) DEFAULT NULL,
  city varchar(255) NOT NULL,
  country varchar(75) NOT NULL,
  user_id bigint NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES local_user (id)
);
--
-- Table structure for table `inventory`
--
DROP TABLE IF EXISTS inventory;
CREATE TABLE inventory (
  id bigint NOT NULL AUTO_INCREMENT,
  quantity int NOT NULL,
  product_id bigint NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (product_id) REFERENCES product(id)
);

--
-- Table structure for table `verification_token`
--

DROP TABLE IF EXISTS verification_token;
CREATE TABLE verification_token (
  id bigint NOT NULL AUTO_INCREMENT,
  created_timestamp datetime(6) NOT NULL,
  token varchar(191) NOT NULL UNIQUE,
  user_id bigint NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES local_user (id)
);

--
-- Table structure for table `web_order`
--

DROP TABLE IF EXISTS web_order;
CREATE TABLE web_order (
  id bigint NOT NULL AUTO_INCREMENT,
  address_id bigint NOT NULL,
  user_id bigint NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (address_id) REFERENCES address(id),
  FOREIGN KEY (user_id) REFERENCES local_user(id)
);

--
-- Table structure for table `web_order_quantities`
--

DROP TABLE IF EXISTS web_order_quantities;

CREATE TABLE web_order_quantities (
  id bigint NOT NULL AUTO_INCREMENT,
  quantity int NOT NULL,
  order_id bigint NOT NULL,
  product_id bigint NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (order_id) REFERENCES web_order (id),
  FOREIGN KEY (product_id) REFERENCES product (id)
);

-- Script to insert dummy dev data into the database.

-- You first need to register two users into the system before running this scirpt.
INSERT INTO local_user (email, first_name, last_name, password, username, email_verified)
    VALUES ('UserA@junit.com', 'UserA-FirstName', 'UserA-LastName', '$2a$10$hBn5gu6cGelJNiE6DDsaBOmZgyumCSzVwrOK/37FWgJ6aLIdZSSI2', 'UserA', true),
    ('UserB@junit.com', 'UserB-FirstName', 'UserB-LastName', '$2a$10$TlYbg57fqOy/1LJjispkjuSIvFJXbh3fy0J9fvHnCpuntZOITAjVG', 'UserB', false),
    ('UserC@junit.com', 'UserC-FirstName', 'UserC-LastName', '$2a$10$SYiYAIW80gDh39jwSaPyiuKGuhrLi7xTUjocL..NOx/1COWe5P03.', 'UserC', false);

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

