package com.example.demo.entity.actual;

import com.baomidou.mybatisplus.annotation.*;
import com.example.demo.entity.BaseReport;
import com.example.demo.service.common.ISumReportService;
import lombok.Data;

import java.math.BigDecimal;

@TableName("total_division")
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
      setActualWorkAmount(new BigDecimal(0));
    } else setActualWorkAmount(value);
  }

  @Override
  public void pushSynthesisUnitprice(BigDecimal value) {

    if (value == null) {
      setActualCostUnitprice(new BigDecimal(0));
    } else setActualCostUnitprice(value);
  }
  ;

  @Override
  public void pushSynthesisSumprice(BigDecimal value) {

    if (value == null) {
      setActualCostSumprice(new BigDecimal(0));
    } else setActualCostSumprice(value);
  }

  @Override
  public BigDecimal fetchWorkAmount() {
    return getActualWorkAmount() == null ? new BigDecimal(0) : getActualWorkAmount();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisUnitprice() {
    return getActualCostUnitprice() == null ? new BigDecimal(0) : getActualCostUnitprice();
  }
  ;

  @Override
  public BigDecimal fetchSynthesisSumprice() {
    return getActualCostSumprice() == null ? new BigDecimal(0) : getActualCostSumprice();
  }
}
