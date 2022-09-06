package com.example.demo.controller.common;

import lombok.Data;

@Data
public class DataMeasure {
  String sort; // 序号
  String code; // 编码
  String name; // 名称
  String distinction; // 项目特征
  String unit; // 计量单位
  String workAmount; // 招标工作量
  String synthesis_unitprice; // 综合单价
  String synthesis_sumprice; // 综合合价
}