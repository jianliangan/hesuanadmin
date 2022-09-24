package com.example.demo.entity.actual;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.BaseReport;
import com.example.demo.service.common.ISumReportService;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ActualDivision extends BaseReport implements ISumReportService {
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
  private BigDecimal budgetWorkAmount;
  private BigDecimal costUnitprice;
  private BigDecimal costSumprice;
  private BigDecimal costManprice;
  private BigDecimal costMaterialsprice;
  private BigDecimal costMechanicsprice;
  private BigDecimal costDeviceprice;
  private BigDecimal costSubpackageprice;

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
      setWorkAmount(new BigDecimal(0));
    } else setWorkAmount(value);
  }

  @Override
  public void pushSynthesisUnitprice(BigDecimal value) {

    if (value == null) {
      setCostUnitprice(new BigDecimal(0));
    } else setCostUnitprice(value);
  }
  ;

  @Override
  public void pushSynthesisSumprice(BigDecimal value) {

    if (value == null) {
      setCostSumprice(new BigDecimal(0));
    } else setCostSumprice(value);
  }

  @Override
  public BigDecimal fetchWorkAmount() {
    return getWorkAmount() == null ? new BigDecimal(0) : getWorkAmount();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisUnitprice() {
    return getCostUnitprice() == null ? new BigDecimal(0) : getCostUnitprice();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisSumprice() {
    return getCostSumprice() == null ? new BigDecimal(0) : getCostSumprice();
  }
}
