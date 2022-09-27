package com.example.demo.entity.dict;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.Base;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubPackage extends Base {
  @TableId(type = IdType.INPUT)
  private String subPackageId;

  private String subPackageName;
  private String contact;
  private String phone;
  private BigDecimal sort;

  @Override
  public Object fetchPrimeId() {
    return subPackageId;
  }

  @Override
  public void pushPrimeId(Object value) {
    subPackageId = value.toString();
  }

  @Override
  public Object fetchParentId() {
    return null;
  }
}
