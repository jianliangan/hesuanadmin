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
  public void pushCostUnitprice2(BigDecimal value);

  /**
   * 成本总价
   *
   * @param value
   */
  public void pushCostSumprice2(BigDecimal value);
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
  public BigDecimal fetchCostUnitprice2();

  /**
   * 成本总价
   *
   * @return
   */
  public BigDecimal fetchCostSumprice2();
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
  public void pushCostUnitprice3(BigDecimal value);

  /**
   * 成本总价
   *
   * @param value
   */
  public void pushCostSumprice3(BigDecimal value);
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
  public BigDecimal fetchCostUnitprice3();

  /**
   * 成本总价
   *
   * @return
   */
  public BigDecimal fetchCostSumprice3();
}
