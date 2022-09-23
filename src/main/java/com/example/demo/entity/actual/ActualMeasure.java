package com.example.demo.entity.actual;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.Base;
import com.example.demo.service.common.ISumReportService;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ActualMeasure extends Base implements ISumReportService {
  @TableId(type = IdType.INPUT)
  private String measureId;

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
    return measureId;
  }

  @Override
  public void pushPrimeId(Object value) {
    measureId = value.toString();
  }

  @Override
  public Object fetchParentId() {
    return parentId;
  }

  @Override
  public void pushWorkAmount(BigDecimal value) {
    setWorkAmount(value);
  }

  @Override
  public void pushSynthesisUnitprice(BigDecimal value) {
    setCostUnitprice(value);
  }
  ;

  @Override
  public void pushSynthesisSumprice(BigDecimal value) {
    setCostSumprice(value);
  }

  @Override
  public BigDecimal fetchWorkAmount() {
    return getWorkAmount();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisUnitprice() {
    return getCostUnitprice();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisSumprice() {
    return getCostSumprice();
  }
}
