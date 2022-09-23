package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Base {
  @TableField(exist = false)
  private String cmd;

  @TableField(exist = false)
  private int sortR;

  @TableField(exist = false)
  private List<Base> children = new ArrayList<Base>();

  @TableField(exist = false)
  private String source;

  public static String ab;

  public abstract Object fetchPrimeId();

  public abstract void pushPrimeId(Object value);

  public abstract Object fetchParentId();
}
