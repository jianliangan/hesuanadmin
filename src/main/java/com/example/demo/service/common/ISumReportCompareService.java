package com.example.demo.service.common;

import java.math.BigDecimal;

public interface ISumReportCompareService {
  /**
   * 招标工程量
   *
   * @param value
   */
  public void pushWorkAmount2(BigDecimal value);

  /**
   * 成本单价
   *
   * @param value
   */
  public void pushSynthesisUnitprice2(BigDecimal value);

  /**
   * 成本总价
   *
   * @param value
   */
  public void pushSynthesisSumprice2(BigDecimal value);
  /**
   * 招标工程量
   *
   * @return
   */
  public BigDecimal fetchWorkAmount2();
  /**
   * 成本单价
   *
   * @return
   */
  public BigDecimal fetchSynthesisUnitprice2();

  /**
   * 成本总价
   *
   * @return
   */
  public BigDecimal fetchSynthesisSumprice2();
  ///////////////////
  /**
   * 招标工程量
   *
   * @param value
   */
  public void pushWorkAmount3(BigDecimal value);

  /**
   * 成本单价
   *
   * @param value
   */
  public void pushSynthesisUnitprice3(BigDecimal value);

  /**
   * 成本总价
   *
   * @param value
   */
  public void pushSynthesisSumprice3(BigDecimal value);
  /**
   * 招标工程量
   *
   * @return
   */
  public BigDecimal fetchWorkAmount3();
  /**
   * 成本单价
   *
   * @return
   */
  public BigDecimal fetchSynthesisUnitprice3();

  /**
   * 成本总价
   *
   * @return
   */
  public BigDecimal fetchSynthesisSumprice3();
}
