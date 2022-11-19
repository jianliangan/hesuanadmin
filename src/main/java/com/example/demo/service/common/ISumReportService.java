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
  public void pushCostUnitprice(BigDecimal value);

  /**
   * 成本总价
   *
   * @param value
   */
  public void pushCostSumprice(BigDecimal value);
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
  public BigDecimal fetchCostUnitprice();

  /**
   * 成本总价
   *
   * @return
   */
  public BigDecimal fetchCostSumprice();
}
