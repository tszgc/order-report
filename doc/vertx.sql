-- MySQL Script generated by MySQL Workbench
-- Wed Jul 13 22:18:51 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema vertx
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema vertx
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `vertx` DEFAULT CHARACTER SET utf8mb4 ;
USE `vertx` ;

-- -----------------------------------------------------
-- Table `vertx`.`t_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vertx`.`t_order` (
                                                 `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单ID',
                                                 `user_id` BIGINT NOT NULL COMMENT '用户ID',
                                                 `status` INT NOT NULL DEFAULT 1 COMMENT '1:已完成',
                                                 `create_at` DATETIME NOT NULL COMMENT '下单时间',
                                                 `update_at` DATETIME NOT NULL COMMENT '修改时间',
                                                 PRIMARY KEY (`id`),
                                                 INDEX `idx_update_at` USING BTREE (`update_at`) COMMENT '更新时间索引')
    ENGINE = InnoDB
    COMMENT = '订单表';


-- -----------------------------------------------------
-- Table `vertx`.`t_order_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vertx`.`t_order_detail` (
                                                        `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                        `order_id` INT NOT NULL COMMENT '订单ID',
                                                        `price` DECIMAL(9,2) NOT NULL COMMENT '价格',
                                                        `num` INT NOT NULL COMMENT '数量',
                                                        `goods_id` BIGINT NOT NULL COMMENT '商品ID',
                                                        `create_at` DATETIME NOT NULL COMMENT '创建时间',
                                                        `update_at` DATETIME NOT NULL COMMENT '修改时间',
                                                        PRIMARY KEY (`id`),
                                                        INDEX `idx_order_id` (`order_id` ASC))
    ENGINE = InnoDB
    COMMENT = '订单明细表';


-- -----------------------------------------------------
-- Table `vertx`.`t_report_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vertx`.`t_report_order` (
                                                        `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单详情id',
                                                        `order_id` BIGINT NOT NULL COMMENT '订单ID',
                                                        `total_num` INT NOT NULL COMMENT '总数量',
                                                        `total_amount` DECIMAL(9,2) NOT NULL COMMENT '总金额',
                                                        `create_at` DATETIME NOT NULL COMMENT '创建时间',
                                                        `update_at` DATETIME NOT NULL COMMENT '修改时间',
                                                        PRIMARY KEY (`id`),
                                                        UNIQUE INDEX `uk_order_id` (`order_id` ASC))
    ENGINE = InnoDB
    COMMENT = '统计报表_订单';


-- -----------------------------------------------------
-- Table `vertx`.`t_report_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vertx`.`t_report_user` (
                                                       `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                       `user_id` BIGINT NOT NULL COMMENT '用户ID',
                                                       `total_num` BIGINT NOT NULL COMMENT '总数量',
                                                       `total_amount` DECIMAL(9,2) NOT NULL COMMENT '总金额',
                                                       `create_at` DATETIME NULL,
                                                       `update_at` DATETIME NULL,
                                                       PRIMARY KEY (`id`),
                                                       UNIQUE INDEX `uk_user_id` (`user_id` ASC))
    ENGINE = InnoDB
    COMMENT = '统计报表_用户';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
