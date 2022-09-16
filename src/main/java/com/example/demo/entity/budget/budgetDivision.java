package com.example.demo.entity.budget;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.Base;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetDivision extends Base {
  @TableId(type = IdType.ASSIGN_ID)
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
  private Integer projectId;
  private String parentId;
  private Integer tag;

  @Override
  public Object getPrimeId() {
    return divisionId;
  }

  @Override
  public void setPrimeId(Object value) {
    divisionId = value.toString();
  }

  @Override
  public Object getParentId() {
    return parentId;
  }
}
