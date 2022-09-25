package com.example.demo.entity.plan;

import com.baomidou.mybatisplus.annotation.*;
import com.example.demo.entity.BaseReport;
import com.example.demo.service.common.ISumReportService;
import lombok.Data;

import java.math.BigDecimal;

@TableName("total_division")
@Data
public class PlanDivision extends BaseReport implements ISumReportService {

  @TableId(type = IdType.INPUT)
  private String divisionId;

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

  @Override
  public void pushWorkAmount(BigDecimal value) {

    if (value == null) {
      setPlanWorkAmount(new BigDecimal(0));
    } else setPlanWorkAmount(value);
  }

  @Override
  public void pushSynthesisUnitprice(BigDecimal value) {
    if (value == null) {
      setPlanCostUnitprice(new BigDecimal(0));
    } else setPlanCostUnitprice(value);
  }
  ;

  @Override
  public void pushSynthesisSumprice(BigDecimal value) {

    if (value == null) {
      setPlanCostSumprice(new BigDecimal(0));
    } else setPlanCostSumprice(value);
  }

  @Override
  public BigDecimal fetchWorkAmount() {
    return getPlanWorkAmount() == null ? new BigDecimal(0) : getPlanWorkAmount();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisUnitprice() {
    return getPlanCostUnitprice() == null ? new BigDecimal(0) : getPlanCostUnitprice();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisSumprice() {
    return getPlanCostSumprice() == null ? new BigDecimal(0) : getPlanCostSumprice();
  }
}
