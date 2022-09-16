package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Project extends Base {
  @TableId(type = IdType.AUTO)
  private Integer projectId;

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

  private Integer parentId;
  private Integer sort;
  private Integer ownId;

  @Override
  public Object getPrimeId() {
    return projectId;
  }

  @Override
  public void setPrimeId(Object value) {}

  @Override
  public Object getParentId() {
    return parentId;
  }
}
