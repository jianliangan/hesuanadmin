package com.example.demo.entity.report;

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
public class TotalDivision extends BaseReport implements ISumReportService {
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

  private BigDecimal planWorkAmount;
  private BigDecimal planCostUnitprice;
  private BigDecimal planCostSumprice;
  private BigDecimal planCostManprice;
  private BigDecimal planCostMaterialsprice;
  private BigDecimal planCostMechanicsprice;
  private BigDecimal planCostDeviceprice;
  private BigDecimal planCostSubpackageprice;

  private BigDecimal actualWorkAmount;
  private BigDecimal actualCostUnitprice;
  private BigDecimal actualCostSumprice;
  private BigDecimal actualCostManprice;
  private BigDecimal actualCostMaterialsprice;
  private BigDecimal actualCostMechanicsprice;
  private BigDecimal actualCostDeviceprice;
  private BigDecimal actualCostSubpackageprice;

  private BigDecimal sort;
  private String ownId;
  private String parentId;
  private Integer tag;

  @TableField(exist = false)
  private String projectName = "";

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
    //    if (value == null) {
    //      setBudgetWorkAmount(new BigDecimal(0));
    //    } else setBudgetWorkAmount(value);
  }

  @Override
  public void pushSynthesisUnitprice(BigDecimal value) {
    //    if (value == null) {
    //      setBudgetSynthesisUnitprice(new BigDecimal(0));
    //    } else setBudgetSynthesisUnitprice(value);
  }
  ;

  @Override
  public void pushSynthesisSumprice(BigDecimal value) {
    //    if (value == null) {
    //      setBudgetSynthesisSumprice(new BigDecimal(0));
    //    } else setBudgetSynthesisSumprice(value);
  }

  @Override
  public BigDecimal fetchWorkAmount() {
    //    return getBudgetWorkAmount() == null ? new BigDecimal(0) : getBudgetWorkAmount();
    return new BigDecimal(0);
  }
  ;

  @Override
  public BigDecimal fetchSynthesisUnitprice() {
    //    return getBudgetSynthesisUnitprice() == null
    //        ? new BigDecimal(0)
    //        : getBudgetSynthesisUnitprice();
    return new BigDecimal(0);
  }
  ;

  @Override
  public BigDecimal fetchSynthesisSumprice() {
    //    return getBudgetSynthesisSumprice() == null ? new BigDecimal(0) :
    // getBudgetSynthesisSumprice();
    return new BigDecimal(0);
  }
  /////////////////////
  @Override
  public void pushBudgetWorkAmount(BigDecimal value) {
    if (value == null) {
      setBudgetWorkAmount(new BigDecimal(0));
    } else setBudgetWorkAmount(value);
  }

  @Override
  public void pushBudgetSynthesisUnitprice(BigDecimal value) {
    if (value == null) {
      setBudgetSynthesisUnitprice(new BigDecimal(0));
    } else setBudgetSynthesisUnitprice(value);
  }
  ;

  @Override
  public void pushBudgetSynthesisSumprice(BigDecimal value) {
    if (value == null) {
      setBudgetSynthesisSumprice(new BigDecimal(0));
    } else setBudgetSynthesisSumprice(value);
  }

  @Override
  public BigDecimal fetchBudgetWorkAmount() {
    return getBudgetWorkAmount() == null ? new BigDecimal(0) : getBudgetWorkAmount();
  }
  ;

  @Override
  public BigDecimal fetchBudgetSynthesisUnitprice() {
    return getBudgetSynthesisUnitprice() == null
        ? new BigDecimal(0)
        : getBudgetSynthesisUnitprice();
  }
  ;

  @Override
  public BigDecimal fetchBudgetSynthesisSumprice() {
    return getBudgetSynthesisSumprice() == null ? new BigDecimal(0) : getBudgetSynthesisSumprice();
  }
  ///////////////
  /////////////////////
  @Override
  public void pushPlanWorkAmount(BigDecimal value) {
    if (value == null) {
      setPlanWorkAmount(new BigDecimal(0));
    } else setPlanWorkAmount(value);
  }

  @Override
  public void pushPlanSynthesisUnitprice(BigDecimal value) {
    if (value == null) {
      setPlanCostUnitprice(new BigDecimal(0));
    } else setPlanCostUnitprice(value);
  }
  ;

  @Override
  public void pushPlanSynthesisSumprice(BigDecimal value) {
    if (value == null) {
      setPlanCostSumprice(new BigDecimal(0));
    } else setPlanCostSumprice(value);
  }

  @Override
  public BigDecimal fetchPlanWorkAmount() {
    return getPlanWorkAmount() == null ? new BigDecimal(0) : getPlanWorkAmount();
  }
  ;

  @Override
  public BigDecimal fetchPlanSynthesisUnitprice() {
    return getPlanCostUnitprice() == null ? new BigDecimal(0) : getPlanCostUnitprice();
  }
  ;

  @Override
  public BigDecimal fetchPlanSynthesisSumprice() {
    return getPlanCostSumprice() == null ? new BigDecimal(0) : getPlanCostSumprice();
  }
  //////////////////
  @Override
  public void pushActualWorkAmount(BigDecimal value) {
    if (value == null) {
      setActualWorkAmount(new BigDecimal(0));
    } else setActualWorkAmount(value);
  }

  @Override
  public void pushActualSynthesisUnitprice(BigDecimal value) {
    if (value == null) {
      setActualCostUnitprice(new BigDecimal(0));
    } else setActualCostUnitprice(value);
  }
  ;

  @Override
  public void pushActualSynthesisSumprice(BigDecimal value) {
    if (value == null) {
      setActualCostSumprice(new BigDecimal(0));
    } else setActualCostSumprice(value);
  }

  @Override
  public BigDecimal fetchActualWorkAmount() {
    return getActualWorkAmount() == null ? new BigDecimal(0) : getActualWorkAmount();
  }
  ;

  @Override
  public BigDecimal fetchActualSynthesisUnitprice() {
    return getActualCostUnitprice() == null ? new BigDecimal(0) : getActualCostUnitprice();
  }
  ;

  @Override
  public BigDecimal fetchActualSynthesisSumprice() {
    return getActualCostSumprice() == null ? new BigDecimal(0) : getActualCostSumprice();
  }
}
