package com.example.demo.entity.budget;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.Base;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetMeasure extends Base {
  @TableId(type = IdType.INPUT)
  private String measureId;

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
  private Integer projectId;
  private String parentId;
  private Integer tag;

  @Override
  public Object getPrimeId() {
    return measureId;
  }

  @Override
  public void setPrimeId(Object value) {}

  @Override
  public Object getParentId() {
    return parentId;
  }
}
