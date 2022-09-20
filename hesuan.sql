-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2022-09-19 19:33:04
-- 服务器版本： 8.0.30-0ubuntu0.20.04.2
-- PHP 版本： 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `hesuan`
--
CREATE DATABASE IF NOT EXISTS `hesuan` DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci;
USE `hesuan`;

-- --------------------------------------------------------

--
-- 表的结构 `budget_division`
--

DROP TABLE IF EXISTS `budget_division`;
CREATE TABLE IF NOT EXISTS `budget_division` (
  `division_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '分部分项id',
  `subject` varchar(200) COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '成本科目',
  `code` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '编码',
  `category` varchar(200) COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '类别',
  `name` varchar(200) COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '名称',
  `distinction` varchar(200) COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '项目特征',
  `unit` varchar(200) COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '单位',
  `have` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '含量',
  `work_amount` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '招标工程量',
  `synthesis_unitprice` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '综合单价',
  `synthesis_sumprice` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '综合合价',
  `manage_unitprice` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '管理费单价',
  `profit_unitprice` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '利润单价',
  `manage_sumprice` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '管理费合价',
  `profit_sumprice` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '利润合价',
  `own_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `sort` decimal(20,10) NOT NULL DEFAULT '0.0000000000',
  `parent_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `tag` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`division_id`) USING BTREE,
  KEY `own_id` (`own_id`,`sort`),
  KEY `sort` (`parent_id`,`sort`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci COMMENT='预算分部分项' ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `budget_division_machine`
--

DROP TABLE IF EXISTS `budget_division_machine`;
CREATE TABLE IF NOT EXISTS `budget_division_machine` (
  `id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '主键id',
  `code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '编码',
  `category` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '类别',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '名称',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '规格型号',
  `unit` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '单位',
  `loss` decimal(13,2) NOT NULL DEFAULT '0.00' COMMENT '损耗率',
  `have` decimal(13,2) NOT NULL DEFAULT '0.00' COMMENT '含量',
  `count` decimal(13,2) NOT NULL DEFAULT '0.00' COMMENT '数量',
  `price` decimal(13,2) NOT NULL DEFAULT '0.00' COMMENT '市场价',
  `combined_price` decimal(13,2) NOT NULL DEFAULT '0.00' COMMENT '合价',
  `parent_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `sort` decimal(20,10) NOT NULL DEFAULT '0.0000000000',
  `own_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `tag` int NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci COMMENT='预算分部分项工料机' ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `budget_measure`
--

DROP TABLE IF EXISTS `budget_measure`;
CREATE TABLE IF NOT EXISTS `budget_measure` (
  `measure_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '措施分项id',
  `subject` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '成本科目',
  `code` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '编码',
  `category` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '类别',
  `name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '名称',
  `distinction` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '项目特征',
  `unit` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '单位',
  `have` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '含量',
  `work_amount` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '招标工程量',
  `synthesis_unitprice` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '综合单价',
  `synthesis_sumprice` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '综合合价',
  `manage_unitprice` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '管理费单价',
  `profit_unitprice` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '利润单价',
  `manage_sumprice` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '管理费合价',
  `profit_sumprice` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '利润合价',
  `own_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `sort` decimal(20,10) NOT NULL DEFAULT '0.0000000000',
  `parent_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `tag` int NOT NULL,
  PRIMARY KEY (`measure_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci COMMENT='预算措施分项' ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `budget_other`
--

DROP TABLE IF EXISTS `budget_other`;
CREATE TABLE IF NOT EXISTS `budget_other` (
  `other_id` int NOT NULL AUTO_INCREMENT COMMENT '措施分项id',
  `name` varchar(200) COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '名称',
  `division_manage_cost` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '分部分项管理费①',
  `measure_manage_cost` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '措施项目管理费②',
  `in_profess_manage_cost` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '其中：专业分包管理费③',
  `in_labour_manage_cost` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '其中：劳务分包管理费④',
  `total` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '管理费合计①+②-③-④',
  PRIMARY KEY (`other_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci COMMENT='预算其他费用' ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `construction`
--

DROP TABLE IF EXISTS `construction`;
CREATE TABLE IF NOT EXISTS `construction` (
  `construction_id` int NOT NULL AUTO_INCREMENT COMMENT '工程id',
  `project_id` int NOT NULL DEFAULT '0' COMMENT '项目id',
  `construction_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '工程名称',
  `duration` int NOT NULL DEFAULT '0' COMMENT '工期',
  `comment` varchar(200) COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `sort` decimal(20,10) NOT NULL,
  `ismerge` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`construction_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci COMMENT='工程表' ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `project`
--

DROP TABLE IF EXISTS `project`;
CREATE TABLE IF NOT EXISTS `project` (
  `project_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '项目id',
  `project_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '项目名称',
  `province` varchar(10) COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '省',
  `city` varchar(10) COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '市',
  `region` varchar(10) COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '区',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开工日期',
  `complete_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '竣工日期',
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '业主姓名',
  `nature` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '业务性质',
  `category` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '工程类别',
  `category_detail` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '工程类别细项',
  `status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '项目状态',
  `contract_price` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '合同额',
  `final_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结算时间',
  `estimate_income` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '预计总收入',
  `estimate_cost` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '预计总成本',
  `tax_way` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '计税方式',
  `parent_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `sort` int NOT NULL DEFAULT '0',
  `own_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`project_id`) USING BTREE,
  KEY `parent_id_2` (`parent_id`,`sort`),
  KEY `sort` (`own_id`,`sort`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci COMMENT='项目表' ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `project_index`
--

DROP TABLE IF EXISTS `project_index`;
CREATE TABLE IF NOT EXISTS `project_index` (
  `index_id` int NOT NULL AUTO_INCREMENT COMMENT '工程id',
  `project_id` int NOT NULL DEFAULT '0' COMMENT '项目id',
  `node_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '树节点名称',
  `comment` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `sort` int NOT NULL DEFAULT '0',
  `parent_id` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`index_id`) USING BTREE,
  KEY `parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci COMMENT='项目下的索引树' ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `test`
--

DROP TABLE IF EXISTS `test`;
CREATE TABLE IF NOT EXISTS `test` (
  `eee` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
