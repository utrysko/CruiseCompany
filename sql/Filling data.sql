USE `cruise`;
-- -----------------------------------------------------
-- Filling role table
-- -----------------------------------------------------
INSERT INTO role (name) VALUES ('admin');
INSERT INTO role (name) VALUES ('client');
-- -----------------------------------------------------
-- Filling user table; password for all this user == 12345
-- -----------------------------------------------------
INSERT INTO user (login, first_name, last_name, email, password, role_id, balance) VALUES ('frodo', 'Frodo', 'Baggins', 'frodoBaggins@gmail.com', '$s0$e0801$KgKOAjrxkNli3AKqQR0EiA==$Zs3g4yt9D6bFYPX7UkdQVpTqbE0yf3OuWd2oWuGQgeA=', 1, 0.0);
INSERT INTO user (login, first_name, last_name, email, password, role_id, balance) VALUES ('harry', 'Harry', 'Potter', 'harryPotter@gmail.com', '$s0$e0801$KgKOAjrxkNli3AKqQR0EiA==$Zs3g4yt9D6bFYPX7UkdQVpTqbE0yf3OuWd2oWuGQgeA=', 2, 0.0);
-- -----------------------------------------------------
-- Filling route table
-- -----------------------------------------------------
INSERT INTO route (number_of_ports, start_port, middle_ports, end_port) VALUES (3, 'Naples', 'Genoa;', 'Monaco');
INSERT INTO route (number_of_ports, start_port, middle_ports, end_port) VALUES (4, 'Palermo', 'Naples;Monaco;', 'Genoa');
INSERT INTO route (number_of_ports, start_port, middle_ports, end_port) VALUES (5, 'Marseille', 'Barcelona;Valencia;Malaga;', 'Rabat');
INSERT INTO route (number_of_ports, start_port, middle_ports, end_port) VALUES (3, 'Monaco', 'Palermo;', 'Naples');
INSERT INTO route (number_of_ports, start_port, middle_ports, end_port) VALUES (4, 'Genoa', 'Palermo;Monaco;', 'Marseille');
-- -----------------------------------------------------
-- Filling cruise_ship table
-- -----------------------------------------------------
INSERT INTO cruise_ship (name, capacity, status, available_from) VALUES ('Rosa', 150, 'Used', '2023-03-22');
INSERT INTO cruise_ship (name, capacity, status, available_from) VALUES ('Naomi', 50, 'Used', '2023-03-24');
INSERT INTO cruise_ship (name, capacity, status, available_from) VALUES ('Cerberus', 100, 'Available', '2023-01-18');
INSERT INTO cruise_ship (name, capacity, status, available_from) VALUES ('Кайзер', 200, 'Used', '2023-03-25');
INSERT INTO cruise_ship (name, capacity, status, available_from) VALUES ('Kronos', 85, 'Available', '2023-02-01');
-- -----------------------------------------------------
-- Filling staff table
-- -----------------------------------------------------
INSERT INTO staff (first_name, last_name, position, cruise_ship_id) VALUES ('Mike', 'Jordan','Capitan', 1);
INSERT INTO staff (first_name, last_name, position, cruise_ship_id) VALUES ('Tom', 'Smith','Sailor', 1);
INSERT INTO staff (first_name, last_name, position, cruise_ship_id) VALUES ('Jack', 'Jackson','Capitan', 2);
INSERT INTO staff (first_name, last_name, position, cruise_ship_id) VALUES ('John', 'Biden' ,'Sailor', 2);
INSERT INTO staff (first_name, last_name, position, cruise_ship_id) VALUES ('Bred', 'Green','Capitan', 3);
INSERT INTO staff (first_name, last_name, position, cruise_ship_id) VALUES ('Leonardo', 'Black','Sailor', 3);
-- -----------------------------------------------------
-- Filling cruise table
-- -----------------------------------------------------
INSERT INTO cruises ( start, end, status, ticket_price, free_spaces, cruise_ship_id, route_id) VALUES ( '2023-03-15', '2023-03-22', 'Available', 50.0, 150, 1, 1);
INSERT INTO cruises ( start, end, status, ticket_price, free_spaces, cruise_ship_id, route_id) VALUES ( '2023-03-16', '2023-03-24', 'Available', 95.0, 50, 2, 2);
INSERT INTO cruises ( start, end, status, ticket_price, free_spaces, cruise_ship_id, route_id) VALUES ( '2023-01-07', '2023-01-18', 'Ended', 85.0, 100, 3, 3);
INSERT INTO cruises ( start, end, status, ticket_price, free_spaces, cruise_ship_id, route_id) VALUES ( '2023-02-23', '2023-03-25', 'Started', 75.0, 100, 4, 5);