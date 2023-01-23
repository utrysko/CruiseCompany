-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema cruise
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cruise
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cruise` DEFAULT CHARACTER SET utf8mb3 ;
USE `cruise` ;

-- -----------------------------------------------------
-- Table `cruise`.`cruise_ship`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cruise`.`cruise_ship` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `capacity` INT UNSIGNED NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `available_from` DATE NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `cruise`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cruise`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cruise`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cruise`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role_id` INT NOT NULL,
  `balance` DOUBLE UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  INDEX `fk_user_role1_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `cruise`.`role` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `cruise`.`route`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cruise`.`route` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `number_of_ports` INT UNSIGNED NOT NULL,
  `start_port` VARCHAR(45) NOT NULL,
  `middle_ports` VARCHAR(45) NOT NULL,
  `end_port` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `cruise`.`cruises`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cruise`.`cruises` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `start` DATE NOT NULL,
  `end` DATE NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `ticket_price` DOUBLE UNSIGNED NOT NULL,
  `free_spaces` INT UNSIGNED NOT NULL,
  `cruise_ship_id` INT NULL,
  `route_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_cruises_cruise_ship1_idx` (`cruise_ship_id` ASC) VISIBLE,
  INDEX `fk_cruises_route1_idx` (`route_id` ASC) VISIBLE,
  CONSTRAINT `fk_cruises_cruise_ship1`
    FOREIGN KEY (`cruise_ship_id`)
    REFERENCES `cruise`.`cruise_ship` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_cruises_route1`
    FOREIGN KEY (`route_id`)
    REFERENCES `cruise`.`route` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cruise`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cruise`.`ticket` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `cruises_id` INT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `document` LONGBLOB NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_cruise_ship_has_user_user1_idx` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_ticket_cruises1_idx` (`cruises_id` ASC) VISIBLE,
  CONSTRAINT `fk_cruise_ship_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `cruise`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_ticket_cruises1`
    FOREIGN KEY (`cruises_id`)
    REFERENCES `cruise`.`cruises` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `cruise`.`staff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cruise`.`staff` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `position` VARCHAR(45) NOT NULL,
  `cruise_ship_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Staff_cruise_ship1_idx` (`cruise_ship_id` ASC) VISIBLE,
  CONSTRAINT `fk_Staff_cruise_ship1`
    FOREIGN KEY (`cruise_ship_id`)
    REFERENCES `cruise`.`cruise_ship` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
