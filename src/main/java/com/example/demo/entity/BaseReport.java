package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.demo.service.common.ISumReportCompareService;
import lombok.Data;

import java.math.BigDecimal;

@Data
public abstract class BaseReport extends Base implements ISumReportCompareService {
  @TableField(exist = false)
  private BigDecimal workAmountPlan;

  @TableField(exist = false)
  private BigDecimal synthesisUnitpricePlan;

  @TableField(exist = false)
  private BigDecimal synthesisSumpricePlan;

  @TableField(exist = false)
  private BigDecimal workAmountActual;

  @TableField(exist = false)
  private BigDecimal synthesisUnitpriceActual;

  @TableField(exist = false)
  private BigDecimal synthesisSumpriceActual;

  public void pushWorkAmountPlan(BigDecimal value) {
    setWorkAmountPlan(value);
  }

  public void pushSynthesisUnitpricePlan(BigDecimal value) {
    setSynthesisUnitpricePlan(value);
  }
  ;

  public void pushSynthesisSumpricePlan(BigDecimal value) {
    setSynthesisSumpricePlan(value);
  }

  public BigDecimal fetchWorkAmountPlan() {
    return getWorkAmountPlan();
  }
  ;

  public BigDecimal fetchSynthesisUnitpricePlan() {
    return getSynthesisUnitpricePlan();
  }
  ;

  public BigDecimal fetchSynthesisSumpricePlan() {
    return getSynthesisSumpricePlan();
  }

  public void pushWorkAmountActual(BigDecimal value) {
    setWorkAmountActual(value);
  }

  public void pushSynthesisUnitpriceActual(BigDecimal value) {
    setSynthesisUnitpriceActual(value);
  }
  ;

  public void pushSynthesisSumpriceActual(BigDecimal value) {
    setSynthesisSumpriceActual(value);
  }

  public BigDecimal fetchWorkAmountActual() {
    return getWorkAmountActual();
  }
  ;

  public BigDecimal fetchSynthesisUnitpriceActual() {
    return getSynthesisUnitpriceActual();
  }
  ;

  public BigDecimal fetchSynthesisSumpriceActual() {
    return getSynthesisSumpriceActual();
  }
}
