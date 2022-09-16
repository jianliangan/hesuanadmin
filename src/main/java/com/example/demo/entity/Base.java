package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public abstract class Base {
  @TableField(exist = false)
  private String cmd;

  @TableField(exist = false)
  private int sortR;

  @TableField(exist = false)
  private List<Base> children;

  public abstract Object getPrimeId();

  public abstract void setPrimeId(Object value);

  public abstract Object getParentId();
}
