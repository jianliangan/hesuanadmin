package com.example.demo.entity.plan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.Base;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlanOther extends Base {
  @TableId(type = IdType.INPUT)
  private String otherId;

  private String name;
  private BigDecimal cost;

  private BigDecimal sort;
  private String ownId;
  private String parentId;
  private Integer tag;

  @TableField(exist = false)
  private String projectName;

  @Override
  public Object fetchPrimeId() {
    return otherId;
  }

  @Override
  public void pushPrimeId(Object value) {
    otherId = value.toString();
  }

  @Override
  public Object fetchParentId() {
    return parentId;
  }
}
