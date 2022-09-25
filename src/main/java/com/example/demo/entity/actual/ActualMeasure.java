package com.example.demo.entity.actual;

import com.baomidou.mybatisplus.annotation.*;
import com.example.demo.entity.Base;
import com.example.demo.service.common.ISumReportService;
import lombok.Data;

import java.math.BigDecimal;

@TableName("total_measure")
@Data
public class ActualMeasure extends Base implements ISumReportService {
  @TableId(type = IdType.INPUT)
  private String measureId;

  private String subject;

  private String code;

  private String category;
  private String name;
  private String distinction;
  private String unit;
  private BigDecimal have;

  @TableField(updateStrategy = FieldStrategy.NEVER)
  private BigDecimal budgetWorkAmount;

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
  private String projectName;

  @Override
  public Object fetchPrimeId() {
    return measureId;
  }

  @Override
  public void pushPrimeId(Object value) {
    measureId = value.toString();
  }

  @Override
  public Object fetchParentId() {
    return parentId;
  }

  @Override
  public void pushWorkAmount(BigDecimal value) {
    setActualWorkAmount(value);
  }

  @Override
  public void pushSynthesisUnitprice(BigDecimal value) {
    setActualCostUnitprice(value);
  }
  ;

  @Override
  public void pushSynthesisSumprice(BigDecimal value) {
    setActualCostSumprice(value);
  }

  @Override
  public BigDecimal fetchWorkAmount() {
    return getActualWorkAmount();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisUnitprice() {
    return getActualCostUnitprice();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisSumprice() {
    return getActualCostSumprice();
  }
}
