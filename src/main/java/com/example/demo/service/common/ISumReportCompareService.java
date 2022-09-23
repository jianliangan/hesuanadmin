package com.example.demo.service.common;

import java.math.BigDecimal;

public interface ISumReportCompareService {
  /**
   * 招标工程量
   *
   * @param value
   */
  public void pushWorkAmountPlan(BigDecimal value);

  /**
   * 成本单价
   *
   * @param value
   */
  public void pushSynthesisUnitpricePlan(BigDecimal value);

  /**
   * 成本总价
   *
   * @param value
   */
  public void pushSynthesisSumpricePlan(BigDecimal value);
  /**
   * 招标工程量
   *
   * @return
   */
  public BigDecimal fetchWorkAmountPlan();
  /**
   * 成本单价
   *
   * @return
   */
  public BigDecimal fetchSynthesisUnitpricePlan();

  /**
   * 成本总价
   *
   * @return
   */
  public BigDecimal fetchSynthesisSumpricePlan();
  ///////////////////
  /**
   * 招标工程量
   *
   * @param value
   */
  public void pushWorkAmountActual(BigDecimal value);

  /**
   * 成本单价
   *
   * @param value
   */
  public void pushSynthesisUnitpriceActual(BigDecimal value);

  /**
   * 成本总价
   *
   * @param value
   */
  public void pushSynthesisSumpriceActual(BigDecimal value);
  /**
   * 招标工程量
   *
   * @return
   */
  public BigDecimal fetchWorkAmountActual();
  /**
   * 成本单价
   *
   * @return
   */
  public BigDecimal fetchSynthesisUnitpriceActual();

  /**
   * 成本总价
   *
   * @return
   */
  public BigDecimal fetchSynthesisSumpriceActual();
}
