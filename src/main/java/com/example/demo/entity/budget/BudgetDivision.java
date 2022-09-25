package com.example.demo.entity.budget;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.entity.BaseReport;
import com.example.demo.service.common.ISumReportService;
import lombok.Data;

import java.math.BigDecimal;

@TableName("total_division")
@Data
public class BudgetDivision extends BaseReport implements ISumReportService {
  @TableId(type = IdType.INPUT)
  private String divisionId;

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
      setBudgetWorkAmount(new BigDecimal(0));
    } else setBudgetWorkAmount(value);
  }

  @Override
  public void pushSynthesisUnitprice(BigDecimal value) {
    if (value == null) {
      setBudgetSynthesisUnitprice(new BigDecimal(0));
    } else setBudgetSynthesisUnitprice(value);
  }
  ;

  @Override
  public void pushSynthesisSumprice(BigDecimal value) {
    if (value == null) {
      setBudgetSynthesisSumprice(new BigDecimal(0));
    } else setBudgetSynthesisSumprice(value);
  }

  @Override
  public BigDecimal fetchWorkAmount() {
    return getBudgetWorkAmount() == null ? new BigDecimal(0) : getBudgetWorkAmount();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisUnitprice() {
    return getBudgetSynthesisUnitprice() == null
        ? new BigDecimal(0)
        : getBudgetSynthesisUnitprice();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisSumprice() {
    return getBudgetSynthesisSumprice() == null ? new BigDecimal(0) : getBudgetSynthesisSumprice();
  }
}
