package com.example.demo.entity.budget;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.Base;
import com.example.demo.service.common.ISumReportService;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetDivision extends Base implements ISumReportService {
  @TableId(type = IdType.INPUT)
  private String divisionId;

  private String subject;

  private String code;

  private String category;
  private String name;
  private String distinction;
  private String unit;
  private BigDecimal have;
  private BigDecimal workAmount;
  private BigDecimal synthesisUnitprice;
  private BigDecimal synthesisSumprice;
  private BigDecimal manageUnitprice;
  private BigDecimal profitUnitprice;
  private BigDecimal manageSumprice;
  private BigDecimal profitSumprice;
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
    setWorkAmount(value);
  }

  @Override
  public void pushSynthesisUnitprice(BigDecimal value) {
    setSynthesisUnitprice(value);
  }
  ;

  @Override
  public void pushSynthesisSumprice(BigDecimal value) {
    setSynthesisSumprice(value);
  }

  @Override
  public BigDecimal fetchWorkAmount() {
    return getWorkAmount();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisUnitprice() {
    return getSynthesisUnitprice();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisSumprice() {
    return getSynthesisSumprice();
  }
}
