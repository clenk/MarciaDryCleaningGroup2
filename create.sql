SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

#CREATE SCHEMA IF NOT EXISTS `Group2` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
#USE `Group2` ;
Drop database if exists Group2;
create database Group2;
use Group2;

-- -----------------------------------------------------
-- Table `Group2`.`CUSTOMER_DATA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Group2`.`CUSTOMER_DATA` (
  `idCustomer` BIGINT NOT NULL AUTO_INCREMENT ,
  `First` VARCHAR(45) NOT NULL ,
  `Last` VARCHAR(45) NOT NULL ,
  `Street` VARCHAR(128) NULL ,
  `City` VARCHAR(45) NULL ,
  `State` VARCHAR(45) NULL ,
  `Zip` INT NULL ,
  `IsClubMember` TINYINT(1) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`idCustomer`) ,
  UNIQUE INDEX `idCustomer_UNIQUE` (`idCustomer` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Group2`.`PHONE`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Group2`.`PHONE` (
  `idPhone` BIGINT NOT NULL AUTO_INCREMENT ,
  `PhoneNum` CHAR(12) NOT NULL ,
  PRIMARY KEY (`idPhone`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Group2`.`EMAIL`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Group2`.`EMAIL` (
  `idEmail` BIGINT NOT NULL AUTO_INCREMENT ,
  `EmailAddr` VARCHAR(60) NOT NULL ,
  PRIMARY KEY (`idEmail`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Group2`.`ORDER_DATA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Group2`.`ORDER_DATA` (
  `OrderNumber` BIGINT NOT NULL AUTO_INCREMENT ,
  `DateDroppedOff` DATETIME NOT NULL ,
  `DatePromised` DATETIME NOT NULL ,
  `DatePickedUp` DATETIME NULL ,
  `Price` DECIMAL(6,2) NULL ,
  `Tax` DECIMAL(6,2) NULL ,
  `Total` DECIMAL(6,2) NULL ,
  `PaymentMethod` VARCHAR(48) NULL ,
  `CUSTOMER_DATA_idCustomer` BIGINT NOT NULL ,
  PRIMARY KEY (`OrderNumber`) ,
  INDEX `fk_ORDER_DATA_CUSTOMER_DATA1` (`CUSTOMER_DATA_idCustomer` ASC) ,
  CONSTRAINT `fk_ORDER_DATA_CUSTOMER_DATA1`
    FOREIGN KEY (`CUSTOMER_DATA_idCustomer` )
    REFERENCES `Group2`.`CUSTOMER_DATA` (`idCustomer` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Group2`.`ORDER_ITEM_DATA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Group2`.`ORDER_ITEM_DATA` (
  `idOrderItem` BIGINT NOT NULL AUTO_INCREMENT ,
  `ClothingDescription` VARCHAR(128) NULL ,
  `Quantity` INT NULL ,
  `ORDER_DATA_OrderNumber` BIGINT NOT NULL ,
  PRIMARY KEY (`idOrderItem`) ,
  INDEX `fk_ORDER_ITEM_DATA_ORDER_DATA1` (`ORDER_DATA_OrderNumber` ASC) ,
  CONSTRAINT `fk_ORDER_ITEM_DATA_ORDER_DATA1`
    FOREIGN KEY (`ORDER_DATA_OrderNumber` )
    REFERENCES `Group2`.`ORDER_DATA` (`OrderNumber` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Group2`.`SERVICE_DATA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Group2`.`SERVICE_DATA` (
  `idService` INT NOT NULL AUTO_INCREMENT ,
  `ServiceDescription` VARCHAR(45) NOT NULL ,
  `Price` DECIMAL(6,2) NOT NULL ,
  `TimeRequired` TIME NOT NULL ,
  PRIMARY KEY (`idService`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Group2`.`ORDER_ITEM_DATA_has_SERVICE_DATA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Group2`.`ORDER_ITEM_DATA_has_SERVICE_DATA` (
  `ORDER_ITEM_DATA_idOrderItem` BIGINT NOT NULL ,
  `SERVICE_DATA_idService` INT NOT NULL ,
  PRIMARY KEY (`ORDER_ITEM_DATA_idOrderItem`, `SERVICE_DATA_idService`) ,
  INDEX `fk_ORDER_ITEM_DATA_has_SERVICE_DATA_SERVICE_DATA1` (`SERVICE_DATA_idService` ASC) ,
  INDEX `fk_ORDER_ITEM_DATA_has_SERVICE_DATA_ORDER_ITEM_DATA` (`ORDER_ITEM_DATA_idOrderItem` ASC) ,
  CONSTRAINT `fk_ORDER_ITEM_DATA_has_SERVICE_DATA_ORDER_ITEM_DATA`
    FOREIGN KEY (`ORDER_ITEM_DATA_idOrderItem` )
    REFERENCES `Group2`.`ORDER_ITEM_DATA` (`idOrderItem` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ORDER_ITEM_DATA_has_SERVICE_DATA_SERVICE_DATA1`
    FOREIGN KEY (`SERVICE_DATA_idService` )
    REFERENCES `Group2`.`SERVICE_DATA` (`idService` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Group2`.`CUSTOMER_DATA_has_PHONE`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Group2`.`CUSTOMER_DATA_has_PHONE` (
  `CUSTOMER_DATA_idCustomer` BIGINT NOT NULL ,
  `PHONE_idPhone` BIGINT NOT NULL ,
  PRIMARY KEY (`CUSTOMER_DATA_idCustomer`, `PHONE_idPhone`) ,
  INDEX `fk_CUSTOMER_DATA_has_PHONE_PHONE1` (`PHONE_idPhone` ASC) ,
  INDEX `fk_CUSTOMER_DATA_has_PHONE_CUSTOMER_DATA1` (`CUSTOMER_DATA_idCustomer` ASC) ,
  CONSTRAINT `fk_CUSTOMER_DATA_has_PHONE_CUSTOMER_DATA1`
    FOREIGN KEY (`CUSTOMER_DATA_idCustomer` )
    REFERENCES `Group2`.`CUSTOMER_DATA` (`idCustomer` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CUSTOMER_DATA_has_PHONE_PHONE1`
    FOREIGN KEY (`PHONE_idPhone` )
    REFERENCES `Group2`.`PHONE` (`idPhone` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Group2`.`CUSTOMER_DATA_has_EMAIL`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Group2`.`CUSTOMER_DATA_has_EMAIL` (
  `CUSTOMER_DATA_idCustomer` BIGINT NOT NULL ,
  `EMAIL_idEmail` BIGINT NOT NULL ,
  PRIMARY KEY (`CUSTOMER_DATA_idCustomer`, `EMAIL_idEmail`) ,
  INDEX `fk_CUSTOMER_DATA_has_EMAIL_EMAIL1` (`EMAIL_idEmail` ASC) ,
  INDEX `fk_CUSTOMER_DATA_has_EMAIL_CUSTOMER_DATA1` (`CUSTOMER_DATA_idCustomer` ASC) ,
  CONSTRAINT `fk_CUSTOMER_DATA_has_EMAIL_CUSTOMER_DATA1`
    FOREIGN KEY (`CUSTOMER_DATA_idCustomer` )
    REFERENCES `Group2`.`CUSTOMER_DATA` (`idCustomer` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CUSTOMER_DATA_has_EMAIL_EMAIL1`
    FOREIGN KEY (`EMAIL_idEmail` )
    REFERENCES `Group2`.`EMAIL` (`idEmail` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;