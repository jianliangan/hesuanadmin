package com.example.demo.entity.plan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.Base;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlanDivision extends Base {
  @TableId(type = IdType.INPUT)
  private String divisionId;

  private String subject;

  private String code;

  private String category;
  private String name;
  private String distinction;
  private String unit;
  private BigDecimal have;
  private BigDecimal workAmount;
  private BigDecimal budgetWorkAmount;
  private BigDecimal costUnitprice;
  private BigDecimal costSumprice;
  private BigDecimal costManprice;
  private BigDecimal costMaterialsprice;
  private BigDecimal costMechanicsprice;
  private BigDecimal costDeviceprice;
  private BigDecimal costSubpackageprice;

  private BigDecimal sort;
  private String ownId;
  private String parentId;
  private Integer tag;

  @TableField(exist = false)
  private String projectName;

  @Override
  public Object fetchPrimeId() {
    return divisionId;
  }

  @Override
  public void pushPrimeId(Object value) {
    divisionId = value.toString();
  }

  @Override
  public Object fetchParentId() {
    return parentId;
  }
}
