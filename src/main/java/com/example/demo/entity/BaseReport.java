package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.demo.service.common.ISumReportCompareService;
import lombok.Data;

import java.math.BigDecimal;

@Data
public abstract class BaseReport extends Base implements ISumReportCompareService {
  @TableField(exist = false)
  private BigDecimal workAmount2;

  @TableField(exist = false)
  private BigDecimal synthesisUnitprice2;

  @TableField(exist = false)
  private BigDecimal synthesisSumprice2;

  @TableField(exist = false)
  private BigDecimal workAmount3;

  @TableField(exist = false)
  private BigDecimal synthesisUnitprice3;

  @TableField(exist = false)
  private BigDecimal synthesisSumprice3;

  public abstract void pushWorkAmount(BigDecimal value);

  public abstract void pushSynthesisUnitprice(BigDecimal value);
  ;

  public abstract void pushSynthesisSumprice(BigDecimal value);

  public abstract BigDecimal fetchWorkAmount();
  ;

  public abstract BigDecimal fetchSynthesisUnitprice();
  ;

  public abstract BigDecimal fetchSynthesisSumprice();

  public void pushWorkAmount2(BigDecimal value) {
    if (value == null) {
      setWorkAmount2(new BigDecimal(0));
    } else setWorkAmount2(value);
  }

  public void pushSynthesisUnitprice2(BigDecimal value) {

    if (value == null) {
      setSynthesisUnitprice2(new BigDecimal(0));
    } else setSynthesisUnitprice2(value);
  }
  ;

  public void pushSynthesisSumprice2(BigDecimal value) {

    if (value == null) {
      setSynthesisSumprice2(new BigDecimal(0));
    } else setSynthesisSumprice2(value);
  }

  public BigDecimal fetchWorkAmount2() {
    return getWorkAmount2() == null ? new BigDecimal(0) : getWorkAmount2();
  }
  ;

  public BigDecimal fetchSynthesisUnitprice2() {
    return getSynthesisUnitprice2() == null ? new BigDecimal(0) : getSynthesisUnitprice2();
  }
  ;

  public BigDecimal fetchSynthesisSumprice2() {
    return getSynthesisSumprice2() == null ? new BigDecimal(0) : getSynthesisSumprice2();
  }

  public void pushWorkAmount3(BigDecimal value) {

    if (value == null) {
      setWorkAmount3(new BigDecimal(0));
    } else setWorkAmount3(value);
  }

  public void pushSynthesisUnitprice3(BigDecimal value) {

    if (value == null) {
      setSynthesisUnitprice3(new BigDecimal(0));
    } else setSynthesisUnitprice3(value);
  }
  ;

  public void pushSynthesisSumprice3(BigDecimal value) {

    if (value == null) {
      setSynthesisSumprice3(new BigDecimal(0));
    } else setSynthesisSumprice3(value);
  }

  public BigDecimal fetchWorkAmount3() {
    return getWorkAmount3() == null ? new BigDecimal(0) : getWorkAmount3();
  }
  ;

  public BigDecimal fetchSynthesisUnitprice3() {
    return getSynthesisUnitprice3() == null ? new BigDecimal(0) : getSynthesisUnitprice3();
  }
  ;

  public BigDecimal fetchSynthesisSumprice3() {
    return getSynthesisSumprice3() == null ? new BigDecimal(0) : getSynthesisSumprice3();
  }
}
