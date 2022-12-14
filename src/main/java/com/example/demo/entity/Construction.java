package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Construction extends Base {
  @TableId(type = IdType.AUTO)
  private Integer constructionId;

  private Integer projectId;
  private String constructionName;
  private Integer duration;
  private String comment;
  private int sort;
  private int ismerge;

  @Override
  public Object fetchPrimeId() {
    return constructionId;
  }

  @Override
  public void pushPrimeId(Object value) {}

  @Override
  public Object fetchParentId() {
    return null;
  }
}
