package com.example.demo.entity.budget.machine;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.Base;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetMeasureMachine extends Base {
  @TableId(type = IdType.INPUT)
  private String id;

  private String code;

  private String category;

  private String name;
  private String type;

  private String unit;
  private BigDecimal loss;
  private BigDecimal have;
  private BigDecimal count;
  private BigDecimal price;
  private BigDecimal combinedPrice;

  private BigDecimal sort;
  private String ownId;
  private String parentId;
  private Integer tag;

  @Override
  public Object fetchPrimeId() {
    return id;
  }

  @Override
  public void pushPrimeId(Object value) {
    id = value.toString();
  }

  @Override
  public Object fetchParentId() {
    return parentId;
  }
}
