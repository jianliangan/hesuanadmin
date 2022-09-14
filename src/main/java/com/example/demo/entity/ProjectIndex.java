package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProjectIndex extends Base {
  @TableId(type = IdType.AUTO)
  private Integer indexId;

  private String projectId;
  private String nodeName;
  private String comment;
  private BigDecimal sort;
  private Integer parentId;

  @Override
  public Object getPrimeId() {
    return indexId;
  }

  @Override
  public Object getParentId() {
    return parentId;
  }
}
