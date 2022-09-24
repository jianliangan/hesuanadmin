package com.example.demo.entity.dict;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.Base;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubPackage extends Base {
  @TableId(type = IdType.INPUT)
  private String id;

  private String name;
  private String contact;
  private String phone;
  private BigDecimal sort;

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
    return null;
  }
}
