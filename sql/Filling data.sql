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
INSERT INTO user (login, first_name, last_name, email, password, role_id, balance) VALUES ('harry', 'Harry', 'Potter', 'harryPotter@gmail.com', '$s0$e0801$KgKOAjrxkNli3AKqQR0EiA==$Zs3g4yt9D6bFYPX7UkdQVpTqbE0yf3OuWd2oWuGQgeA=', 2, 20.0);
INSERT INTO user (login, first_name, last_name, email, password, role_id, balance) VALUES ('miguel', 'Miguel', 'Been', 'miguelbeen@gmail.com', '$s0$e0801$KgKOAjrxkNli3AKqQR0EiA==$Zs3g4yt9D6bFYPX7UkdQVpTqbE0yf3OuWd2oWuGQgeA=', 2, 150.0);
INSERT INTO user (login, first_name, last_name, email, password, role_id, balance) VALUES ('mark', 'Mark', 'Smith', 'marksmith@gmail.com', '$s0$e0801$KgKOAjrxkNli3AKqQR0EiA==$Zs3g4yt9D6bFYPX7UkdQVpTqbE0yf3OuWd2oWuGQgeA=', 2, 180.0);
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
INSERT INTO cruise_ship (name, capacity, status, available_from) VALUES ('Rosa', 150, 'Used', '2023-05-15');
INSERT INTO cruise_ship (name, capacity, status, available_from) VALUES ('Naomi', 50, 'Used', '2023-02-17');
INSERT INTO cruise_ship (name, capacity, status, available_from) VALUES ('Cerberus', 100, 'Available', '2023-02-18');
INSERT INTO cruise_ship (name, capacity, status, available_from) VALUES ('Кайзер', 200, 'Used', '2023-05-13');
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
INSERT INTO cruises ( start, end, status, ticket_price, free_spaces, cruise_ship_id, route_id) VALUES ( '2023-04-25', '2023-05-15', 'Available', 50.0, 150, 1, 1);
INSERT INTO cruises ( start, end, status, ticket_price, free_spaces, cruise_ship_id, route_id) VALUES ( '2023-04-26', '2023-05-17', 'Available', 95.0, 50, 2, 2);
INSERT INTO cruises ( start, end, status, ticket_price, free_spaces, cruise_ship_id, route_id) VALUES ( '2023-02-07', '2023-02-18', 'Ended', 85.0, 100, 3, 3);
INSERT INTO cruises ( start, end, status, ticket_price, free_spaces, cruise_ship_id, route_id) VALUES ( '2023-04-11', '2023-05-13', 'Started', 75.0, 100, 4, 5);
