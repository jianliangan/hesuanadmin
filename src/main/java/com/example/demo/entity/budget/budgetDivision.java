package com.example.demo.entity.budget;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.Base;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetDivision extends Base {
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
}
