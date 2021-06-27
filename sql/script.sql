-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema intacledb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema intacledb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `intacledb` DEFAULT CHARACTER SET utf8 ;
USE `intacledb` ;

-- -----------------------------------------------------
-- Table `intacledb`.`chats`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intacledb`.`chats` (
                                                   `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                   `title` VARCHAR(45) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 14
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `intacledb`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intacledb`.`users` (
                                                   `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                   `login` VARCHAR(17) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    `name` VARCHAR(25) NULL DEFAULT NULL,
    `surname` VARCHAR(25) NULL DEFAULT NULL,
    `avatar_image_path` VARCHAR(255) NULL DEFAULT NULL,
    `user_level` VARCHAR(11) NULL DEFAULT 'user',
    `activation_code` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 47
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `intacledb`.`chat_membership`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intacledb`.`chat_membership` (
                                                             `chat` BIGINT NOT NULL,
                                                             `user` BIGINT NOT NULL,
                                                             PRIMARY KEY (`chat`, `user`),
    INDEX `fk_chat_membership_user1_idx` (`user` ASC) VISIBLE,
    CONSTRAINT `fk_chat_membership_chat1`
    FOREIGN KEY (`chat`)
    REFERENCES `intacledb`.`chats` (`id`),
    CONSTRAINT `fk_chat_membership_user1`
    FOREIGN KEY (`user`)
    REFERENCES `intacledb`.`users` (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `intacledb`.`entries`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intacledb`.`entries` (
                                                     `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                     `title` VARCHAR(45) NOT NULL,
    `author` BIGINT NOT NULL,
    `summary` LONGTEXT NULL DEFAULT NULL,
    `content` LONGTEXT NULL DEFAULT NULL,
    `creation_date` TIMESTAMP(6) NOT NULL,
    `update_date` TIMESTAMP(6) NOT NULL,
    `preview_image_path` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    INDEX `fk_entries_author1` (`author` ASC) VISIBLE,
    CONSTRAINT `fk_entries_author1`
    FOREIGN KEY (`author`)
    REFERENCES `intacledb`.`users` (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 8
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `intacledb`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intacledb`.`comments` (
                                                      `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                      `author` BIGINT NOT NULL,
                                                      `content` LONGTEXT NOT NULL,
                                                      `creation_date` TIMESTAMP(6) NOT NULL,
    `update_date` TIMESTAMP(6) NOT NULL,
    `entry` BIGINT NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    INDEX `fk_comments_author1_idx` (`author` ASC) VISIBLE,
    INDEX `fk_comments_entry1_idx` (`entry` ASC) VISIBLE,
    CONSTRAINT `fk_comments_author1`
    FOREIGN KEY (`author`)
    REFERENCES `intacledb`.`users` (`id`),
    CONSTRAINT `fk_comments_entry1`
    FOREIGN KEY (`entry`)
    REFERENCES `intacledb`.`entries` (`id`)
    ON DELETE CASCADE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 11
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `intacledb`.`likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intacledb`.`likes` (
                                                   `author` BIGINT NOT NULL,
                                                   `comment` BIGINT NULL DEFAULT NULL,
                                                   `entry` BIGINT NULL DEFAULT NULL,
                                                   INDEX `fk_likes_entry1_idx` (`entry` ASC) VISIBLE,
    INDEX `fk_likes_comment1_idx` (`comment` ASC) VISIBLE,
    INDEX `fk_likes_author1` (`author` ASC) VISIBLE,
    CONSTRAINT `fk_likes_author1`
    FOREIGN KEY (`author`)
    REFERENCES `intacledb`.`users` (`id`),
    CONSTRAINT `fk_likes_comment1`
    FOREIGN KEY (`comment`)
    REFERENCES `intacledb`.`comments` (`id`)
    ON DELETE CASCADE,
    CONSTRAINT `fk_likes_entry1`
    FOREIGN KEY (`entry`)
    REFERENCES `intacledb`.`entries` (`id`)
    ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `intacledb`.`messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intacledb`.`messages` (
                                                      `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                      `author` BIGINT NOT NULL,
                                                      `chat` BIGINT NOT NULL,
                                                      `content` LONGTEXT NOT NULL,
                                                      `creation_date` TIMESTAMP(6) NOT NULL,
    `update_date` TIMESTAMP(6) NOT NULL,
    `is_new` TINYINT(1) NULL DEFAULT '1',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    INDEX `fk_messages_author1_idx` (`author` ASC) VISIBLE,
    INDEX `fk_messages_chat1_idx` (`chat` ASC) VISIBLE,
    CONSTRAINT `fk_messages_author1`
    FOREIGN KEY (`author`)
    REFERENCES `intacledb`.`users` (`id`),
    CONSTRAINT `fk_messages_chat1`
    FOREIGN KEY (`chat`)
    REFERENCES `intacledb`.`chats` (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 54
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `intacledb`.`subscriptions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intacledb`.`subscriptions` (
                                                           `subscription` BIGINT NOT NULL,
                                                           `subscriber` BIGINT NOT NULL,
                                                           PRIMARY KEY (`subscription`, `subscriber`),
    INDEX `fk_followings_follower1_idx` (`subscriber` ASC) VISIBLE,
    CONSTRAINT `fk_followings_follower1`
    FOREIGN KEY (`subscriber`)
    REFERENCES `intacledb`.`users` (`id`),
    CONSTRAINT `fk_followings_following1`
    FOREIGN KEY (`subscription`)
    REFERENCES `intacledb`.`users` (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
