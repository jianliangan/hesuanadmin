DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `project_id` int(11) NOT NULL AUTO_INCREMENT  COMMENT '项目id',  
  `project_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT ''  COMMENT '项目名称',
  `province` varchar(10) NOT NULL DEFAULT '0'  COMMENT '省',
  `city`  varchar(10) NOT NULL DEFAULT '0'  COMMENT '市',
`region`  varchar(10) NOT NULL DEFAULT '0'  COMMENT '区',
`start_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开工日期',
`complete_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '竣工日期',
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT ''  COMMENT '业主姓名',
  `nature` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT ''  COMMENT '业务性质',
  `category` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT ''  COMMENT '工程类别',
  `category_detail` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT ''  COMMENT '工程类别细项',
  `status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT ''  COMMENT '项目状态',
  `contract_price` DECIMAL(19,2) NOT NULL DEFAULT '0' COMMENT '合同额',
`final_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结算时间',
`estimate_income`  DECIMAL(19,2) NOT NULL DEFAULT '0' COMMENT '预计总收入',
`estimate_cost`  DECIMAL(19,2) NOT NULL DEFAULT '0' COMMENT '预计总成本',
`tax_way`  varchar(255)  CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT  NULL DEFAULT '' COMMENT '计税方式',
  PRIMARY KEY (`project_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = Dynamic  COMMENT='项目表';

DROP TABLE IF EXISTS `construction`;
CREATE TABLE `construction`  (
  `construction_id` int(11) NOT NULL AUTO_INCREMENT  COMMENT '工程id',  
   `project_id` int(11) NOT NULL DEFAULT '0'  COMMENT '项目id',  
  `construction_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT ''  COMMENT '工程名称',
  `duration` int(11) NOT NULL DEFAULT '0'  COMMENT '工期',
  `comment`  varchar(200) NOT NULL DEFAULT ''  COMMENT '备注',
  PRIMARY KEY (`construction_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = Dynamic  COMMENT='工程表';

DROP TABLE IF EXISTS `budget_division`;
CREATE TABLE `budget_division`  (
  `division_id` int(11) NOT NULL AUTO_INCREMENT  COMMENT '分部分项id',  
   `subject`  varchar(200) NOT NULL DEFAULT ''  COMMENT '成本科目',
     `code`  varchar(200) NOT NULL DEFAULT ''  COMMENT '编码',
        `category`  varchar(200) NOT NULL DEFAULT ''  COMMENT '类别',
           `name`  varchar(200) NOT NULL DEFAULT ''  COMMENT '名称',
              `distinction`  varchar(200) NOT NULL DEFAULT ''  COMMENT '项目特征',
                 `unit`  varchar(200) NOT NULL DEFAULT ''  COMMENT '单位',
                    `have`  DECIMAL(19,2) NOT NULL DEFAULT '0'  COMMENT '含量',
                                   `work_amount`  DECIMAL(19,2) NOT NULL DEFAULT '0'  COMMENT '招标工程量',
                       `synthesis_unitprice`  DECIMAL(19,2) NOT NULL DEFAULT '0'   COMMENT '综合单价',
                          `synthesis_sumprice`  DECIMAL(19,2) NOT NULL DEFAULT '0'   COMMENT '综合合价',
                                                    `manage_unitprice` DECIMAL(19,2) NOT NULL DEFAULT '0'   COMMENT '管理费单价',
                  `profit_unitprice`  DECIMAL(19,2) NOT NULL DEFAULT '0'   COMMENT '利润单价',
          `manage_sumprice`  DECIMAL(19,2) NOT NULL DEFAULT '0'   COMMENT '管理费合价',
              `profit_sumprice`  DECIMAL(19,2) NOT NULL DEFAULT '0'   COMMENT '利润合价',      
  
  PRIMARY KEY (`division_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = Dynamic  COMMENT='预算分部分项';

DROP TABLE IF EXISTS `budget_measure`;
CREATE TABLE `budget_measure`  (
  `measure_id` int(11) NOT NULL AUTO_INCREMENT  COMMENT '措施分项id',  
  `subject`  varchar(200) NOT NULL DEFAULT ''  COMMENT '成本科目',
     `code`  varchar(200) NOT NULL DEFAULT ''  COMMENT '编码',
        `category`  varchar(200) NOT NULL DEFAULT ''  COMMENT '类别',
           `name`  varchar(200) NOT NULL DEFAULT ''  COMMENT '名称',
              `distinction`  varchar(200) NOT NULL DEFAULT ''  COMMENT '项目特征',
                 `unit`  varchar(200) NOT NULL DEFAULT ''  COMMENT '单位',
                    `have`  DECIMAL(19,2) NOT NULL DEFAULT '0'  COMMENT '含量',
                                   `work_amount`  DECIMAL(19,2) NOT NULL DEFAULT '0'  COMMENT '招标工程量',
                       `synthesis_unitprice`  DECIMAL(19,2) NOT NULL DEFAULT '0'   COMMENT '综合单价',
                          `synthesis_sumprice`  DECIMAL(19,2) NOT NULL DEFAULT '0'   COMMENT '综合合价',
                                                    `manage_unitprice` DECIMAL(19,2) NOT NULL DEFAULT '0'   COMMENT '管理费单价',
                  `profit_unitprice`  DECIMAL(19,2) NOT NULL DEFAULT '0'   COMMENT '利润单价',
          `manage_sumprice`  DECIMAL(19,2) NOT NULL DEFAULT '0'   COMMENT '管理费合价',
              `profit_sumprice`  DECIMAL(19,2) NOT NULL DEFAULT '0'   COMMENT '利润合价',      
  
  PRIMARY KEY (`measure_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = Dynamic  COMMENT='预算措施分项';

DROP TABLE IF EXISTS `budget_other`;
CREATE TABLE `budget_other`  (
  `other_id` int(11) NOT NULL AUTO_INCREMENT  COMMENT '措施分项id',  
  `name`  varchar(200) NOT NULL DEFAULT ''  COMMENT '名称',
     `division_manage_cost`   DECIMAL(19,2) NOT NULL DEFAULT '0'  COMMENT '分部分项管理费①',
        `measure_manage_cost`   DECIMAL(19,2) NOT NULL DEFAULT '0' COMMENT '措施项目管理费②',
           `in_profess_manage_cost`   DECIMAL(19,2) NOT NULL DEFAULT '0'  COMMENT '其中：专业分包管理费③',
              `in_labour_manage_cost`   DECIMAL(19,2) NOT NULL DEFAULT '0'  COMMENT '其中：劳务分包管理费④',
                 `total`   DECIMAL(19,2) NOT NULL DEFAULT '0'  COMMENT '管理费合计①+②-③-④',            
  PRIMARY KEY (`other_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = Dynamic  COMMENT='预算其他费用';