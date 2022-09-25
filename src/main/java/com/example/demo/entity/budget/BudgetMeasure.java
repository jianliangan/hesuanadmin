package com.example.demo.entity.budget;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.entity.Base;
import com.example.demo.service.common.ISumReportService;
import lombok.Data;

import java.math.BigDecimal;

@TableName("total_measure")
@Data
public class BudgetMeasure extends Base implements ISumReportService {
  @TableId(type = IdType.INPUT)
  private String measureId;

  private String subject;

  private String code;
  private String category;
  private String name;
  private String distinction;
  private String unit;
  private BigDecimal have;
  private BigDecimal budgetWorkAmount;
  private BigDecimal budgetSynthesisUnitprice;
  private BigDecimal budgetSynthesisSumprice;
  private BigDecimal budgetManageUnitprice;
  private BigDecimal budgetProfitUnitprice;
  private BigDecimal budgetManageSumprice;
  private BigDecimal budgetProfitSumprice;
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
    setBudgetWorkAmount(value);
  }

  @Override
  public void pushSynthesisUnitprice(BigDecimal value) {
    setBudgetSynthesisUnitprice(value);
  }
  ;

  @Override
  public void pushSynthesisSumprice(BigDecimal value) {
    setBudgetSynthesisSumprice(value);
  }

  @Override
  public BigDecimal fetchWorkAmount() {
    return getBudgetWorkAmount();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisUnitprice() {
    return getBudgetSynthesisUnitprice();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisSumprice() {
    return getBudgetSynthesisSumprice();
  }
}
