package com.example.demo.service.common;

import java.math.BigDecimal;

public interface ISumReportService {
  /**
   * 招标工程量
   *
   * @param value
   */
  public void pushWorkAmount(BigDecimal value);

  /**
   * 成本单价
   *
   * @param value
   */
  public void pushSynthesisUnitprice(BigDecimal value);

  /**
   * 成本总价
   *
   * @param value
   */
  public void pushSynthesisSumprice(BigDecimal value);
  /**
   * 招标工程量
   *
   * @return
   */
  public BigDecimal fetchWorkAmount();
  /**
   * 成本单价
   *
   * @return
   */
  public BigDecimal fetchSynthesisUnitprice();

  /**
   * 成本总价
   *
   * @return
   */
  public BigDecimal fetchSynthesisSumprice();

  //////////////////
  public default void pushBudgetWorkAmount(BigDecimal value) {}
  ;

  /**
   * 成本单价
   *
   * @param value
   */
  public default void pushBudgetSynthesisUnitprice(BigDecimal value) {}
  ;

  /**
   * 成本总价
   *
   * @param value
   */
  public default void pushBudgetSynthesisSumprice(BigDecimal value) {}
  ;
  /**
   * 招标工程量
   *
   * @return
   */
  public default BigDecimal fetchBudgetWorkAmount() {
    return new BigDecimal(0);
  }
  ;
  /**
   * 成本单价
   *
   * @return
   */
  public default BigDecimal fetchBudgetSynthesisUnitprice() {
    return new BigDecimal(0);
  }
  ;

  /**
   * 成本总价
   *
   * @return
   */
  public default BigDecimal fetchBudgetSynthesisSumprice() {
    return new BigDecimal(0);
  }
  ;
  ////////////
  public default void pushPlanWorkAmount(BigDecimal value) {}
  ;

  /**
   * 成本单价
   *
   * @param value
   */
  public default void pushPlanSynthesisUnitprice(BigDecimal value) {}
  ;

  /**
   * 成本总价
   *
   * @param value
   */
  public default void pushPlanSynthesisSumprice(BigDecimal value) {}
  ;
  /**
   * 招标工程量
   *
   * @return
   */
  public default BigDecimal fetchPlanWorkAmount() {
    return new BigDecimal(0);
  }
  ;
  /**
   * 成本单价
   *
   * @return
   */
  public default BigDecimal fetchPlanSynthesisUnitprice() {
    return new BigDecimal(0);
  }
  ;

  /**
   * 成本总价
   *
   * @return
   */
  public default BigDecimal fetchPlanSynthesisSumprice() {
    return new BigDecimal(0);
  }
  ;
  /////////////////
  public default void pushActualWorkAmount(BigDecimal value) {}
  ;

  /**
   * 成本单价
   *
   * @param value
   */
  public default void pushActualSynthesisUnitprice(BigDecimal value) {}
  ;

  /**
   * 成本总价
   *
   * @param value
   */
  public default void pushActualSynthesisSumprice(BigDecimal value) {}
  ;
  /**
   * 招标工程量
   *
   * @return
   */
  public default BigDecimal fetchActualWorkAmount() {
    return new BigDecimal(0);
  }
  ;
  /**
   * 成本单价
   *
   * @return
   */
  public default BigDecimal fetchActualSynthesisUnitprice() {
    return new BigDecimal(0);
  }
  ;

  /**
   * 成本总价
   *
   * @return
   */
  public default BigDecimal fetchActualSynthesisSumprice() {
    return new BigDecimal(0);
  }
  ;
}
