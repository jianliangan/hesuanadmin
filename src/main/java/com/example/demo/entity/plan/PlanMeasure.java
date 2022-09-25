package com.example.demo.entity.plan;

import com.baomidou.mybatisplus.annotation.*;
import com.example.demo.entity.Base;
import com.example.demo.service.common.ISumReportService;
import lombok.Data;

import java.math.BigDecimal;

@TableName("total_measure")
@Data
public class PlanMeasure extends Base implements ISumReportService {
  @TableId(type = IdType.INPUT)
  private String measureId;

  private String subject;

  private String code;

  private String category;
  private String name;
  private String distinction;
  private String unit;
  private BigDecimal have;

  @TableField(updateStrategy = FieldStrategy.NEVER)
  private BigDecimal budgetWorkAmount;

  private BigDecimal planWorkAmount;
  private BigDecimal planCostUnitprice;
  private BigDecimal planCostSumprice;
  private BigDecimal planCostManprice;
  private BigDecimal planCostMaterialsprice;
  private BigDecimal planCostMechanicsprice;
  private BigDecimal planCostDeviceprice;
  private BigDecimal planCostSubpackageprice;

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
    setPlanWorkAmount(value);
  }

  @Override
  public void pushSynthesisUnitprice(BigDecimal value) {
    setPlanCostUnitprice(value);
  }
  ;

  @Override
  public void pushSynthesisSumprice(BigDecimal value) {
    setPlanCostSumprice(value);
  }

  @Override
  public BigDecimal fetchWorkAmount() {
    return getPlanWorkAmount();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisUnitprice() {
    return getPlanCostUnitprice();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisSumprice() {
    return getPlanCostSumprice();
  }
}
