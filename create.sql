SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `mdc` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `mdc` ;

-- -----------------------------------------------------
-- Table `mdc`.`CUSTOMER_DATA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mdc`.`CUSTOMER_DATA` (
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
-- Table `mdc`.`PHONE`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mdc`.`PHONE` (
  `idPhone` BIGINT NOT NULL AUTO_INCREMENT ,
  `PhoneNum` CHAR(12) NOT NULL ,
  PRIMARY KEY (`idPhone`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mdc`.`EMAIL`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mdc`.`EMAIL` (
  `idEmail` BIGINT NOT NULL AUTO_INCREMENT ,
  `EmailAddr` VARCHAR(60) NOT NULL ,
  PRIMARY KEY (`idEmail`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mdc`.`ORDER_DATA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mdc`.`ORDER_DATA` (
  `OrderNumber` BIGINT NOT NULL AUTO_INCREMENT ,
  `DateDroppedOff` DATETIME NOT NULL ,
  `DatePromised` DATETIME NOT NULL ,
  `DatePickedUp` DATETIME NULL ,
  `Price` DECIMAL(6,2) NULL ,
  `Tax` DECIMAL(6,2) NULL ,
  `Total` DECIMAL(6,2) NULL ,
  `PaymentMethod` DECIMAL(6,2) NULL ,
  `CUSTOMER_DATA_idCustomer` BIGINT NOT NULL ,
  PRIMARY KEY (`OrderNumber`) ,
  INDEX `fk_ORDER_DATA_CUSTOMER_DATA1` (`CUSTOMER_DATA_idCustomer` ASC) ,
  CONSTRAINT `fk_ORDER_DATA_CUSTOMER_DATA1`
    FOREIGN KEY (`CUSTOMER_DATA_idCustomer` )
    REFERENCES `mdc`.`CUSTOMER_DATA` (`idCustomer` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mdc`.`ORDER_ITEM_DATA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mdc`.`ORDER_ITEM_DATA` (
  `idOrderItem` BIGINT NOT NULL AUTO_INCREMENT ,
  `ClothingDescription` VARCHAR(128) NULL ,
  `Quantity` INT NULL ,
  `ORDER_DATA_OrderNumber` BIGINT NOT NULL ,
  PRIMARY KEY (`idOrderItem`) ,
  INDEX `fk_ORDER_ITEM_DATA_ORDER_DATA1` (`ORDER_DATA_OrderNumber` ASC) ,
  CONSTRAINT `fk_ORDER_ITEM_DATA_ORDER_DATA1`
    FOREIGN KEY (`ORDER_DATA_OrderNumber` )
    REFERENCES `mdc`.`ORDER_DATA` (`OrderNumber` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mdc`.`SERVICE_DATA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mdc`.`SERVICE_DATA` (
  `idService` INT NOT NULL AUTO_INCREMENT ,
  `ServiceDescription` VARCHAR(45) NOT NULL ,
  `Price` DECIMAL(6,2) NOT NULL ,
  `TimeRequired` TIME NOT NULL ,
  PRIMARY KEY (`idService`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mdc`.`ORDER_ITEM_DATA_has_SERVICE_DATA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mdc`.`ORDER_ITEM_DATA_has_SERVICE_DATA` (
  `ORDER_ITEM_DATA_idOrderItem` BIGINT NOT NULL ,
  `SERVICE_DATA_idService` INT NOT NULL ,
  PRIMARY KEY (`ORDER_ITEM_DATA_idOrderItem`, `SERVICE_DATA_idService`) ,
  INDEX `fk_ORDER_ITEM_DATA_has_SERVICE_DATA_SERVICE_DATA1` (`SERVICE_DATA_idService` ASC) ,
  INDEX `fk_ORDER_ITEM_DATA_has_SERVICE_DATA_ORDER_ITEM_DATA` (`ORDER_ITEM_DATA_idOrderItem` ASC) ,
  CONSTRAINT `fk_ORDER_ITEM_DATA_has_SERVICE_DATA_ORDER_ITEM_DATA`
    FOREIGN KEY (`ORDER_ITEM_DATA_idOrderItem` )
    REFERENCES `mdc`.`ORDER_ITEM_DATA` (`idOrderItem` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ORDER_ITEM_DATA_has_SERVICE_DATA_SERVICE_DATA1`
    FOREIGN KEY (`SERVICE_DATA_idService` )
    REFERENCES `mdc`.`SERVICE_DATA` (`idService` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mdc`.`CUSTOMER_DATA_has_PHONE`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mdc`.`CUSTOMER_DATA_has_PHONE` (
  `CUSTOMER_DATA_idCustomer` BIGINT NOT NULL ,
  `PHONE_idPhone` BIGINT NOT NULL ,
  PRIMARY KEY (`CUSTOMER_DATA_idCustomer`, `PHONE_idPhone`) ,
  INDEX `fk_CUSTOMER_DATA_has_PHONE_PHONE1` (`PHONE_idPhone` ASC) ,
  INDEX `fk_CUSTOMER_DATA_has_PHONE_CUSTOMER_DATA1` (`CUSTOMER_DATA_idCustomer` ASC) ,
  CONSTRAINT `fk_CUSTOMER_DATA_has_PHONE_CUSTOMER_DATA1`
    FOREIGN KEY (`CUSTOMER_DATA_idCustomer` )
    REFERENCES `mdc`.`CUSTOMER_DATA` (`idCustomer` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CUSTOMER_DATA_has_PHONE_PHONE1`
    FOREIGN KEY (`PHONE_idPhone` )
    REFERENCES `mdc`.`PHONE` (`idPhone` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mdc`.`CUSTOMER_DATA_has_EMAIL`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mdc`.`CUSTOMER_DATA_has_EMAIL` (
  `CUSTOMER_DATA_idCustomer` BIGINT NOT NULL ,
  `EMAIL_idEmail` BIGINT NOT NULL ,
  PRIMARY KEY (`CUSTOMER_DATA_idCustomer`, `EMAIL_idEmail`) ,
  INDEX `fk_CUSTOMER_DATA_has_EMAIL_EMAIL1` (`EMAIL_idEmail` ASC) ,
  INDEX `fk_CUSTOMER_DATA_has_EMAIL_CUSTOMER_DATA1` (`CUSTOMER_DATA_idCustomer` ASC) ,
  CONSTRAINT `fk_CUSTOMER_DATA_has_EMAIL_CUSTOMER_DATA1`
    FOREIGN KEY (`CUSTOMER_DATA_idCustomer` )
    REFERENCES `mdc`.`CUSTOMER_DATA` (`idCustomer` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CUSTOMER_DATA_has_EMAIL_EMAIL1`
    FOREIGN KEY (`EMAIL_idEmail` )
    REFERENCES `mdc`.`EMAIL` (`idEmail` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
