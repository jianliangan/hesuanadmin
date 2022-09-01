DROP TABLE `hesuan`.`project`;
CREATE TABLE `project`  (
  `project_id` int(11) NOT NULL AUTO_INCREMENT  COMMENT '项目id',  
  `project_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT ''  COMMENT '项目名称',
  `province` int(11) NOT NULL DEFAULT '0'  COMMENT '省',
  `city`  int(11) NOT NULL DEFAULT '0'  COMMENT '市',
`region`  int(11) NOT NULL DEFAULT '0'  COMMENT '区',
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
