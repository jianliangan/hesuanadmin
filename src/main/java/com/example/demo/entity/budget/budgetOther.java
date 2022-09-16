package com.example.demo.entity.budget;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.Base;

import java.math.BigDecimal;

public class BudgetOther extends Base {
  @TableId(type = IdType.AUTO)
  private String otherId;

  private String name;
  private BigDecimal divisionManageCost;
  private BigDecimal measureManageCost;
  private BigDecimal inProfessManageCost;
  private BigDecimal inLabourManageCost;
  private BigDecimal total;
  private BigDecimal sort;
  private Integer projectId;

  @Override
  public Object getPrimeId() {
    return otherId;
  }

  @Override
  public void setPrimeId(Object value) {}

  @Override
  public Object getParentId() {
    return null;
  }
}
