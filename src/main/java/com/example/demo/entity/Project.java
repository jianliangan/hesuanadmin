package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Project extends Base {
  @TableId(type = IdType.INPUT)
  private String projectId;

  private String projectName;
  private String province;
  private String city;
  private String region;
  private String startTime;

  private String completeTime;
  private String username;
  private String nature;
  private String category;
  private String categoryDetail;

  private String status;
  private BigDecimal contractPrice;
  private String finalTime;
  private BigDecimal estimateIncome;
  private BigDecimal estimateCost;
  private String taxWay;

  private String parentId;
  private Integer sort;
  private String ownId;

  @Override
  public Object fetchPrimeId() {
    return projectId;
  }

  @Override
  public void pushPrimeId(Object value) {
    projectId = value.toString();
  }

  @Override
  public Object fetchParentId() {
    return parentId;
  }
}
