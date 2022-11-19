package com.example.demo.entity.dict;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.Base;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class SupplyUnit extends Base {
    @TableId(type = IdType.INPUT)
    private String SupplyUnitId;
    @Length(max = 50, message = "名称长度不能超过50")
    private String SupplyUnitName;
    @Length(max = 50, message = "联系方式长度不能超过50")
    private String contact;
    @Length(max = 50, message = "手机号长度不能超过50")
    private String phone;
    private String manager;
    @TableField(exist = false)
    private String managerName;
    private Date datetime;
    private BigDecimal sort;
    private String SupplierType;

    @Override
    public Object fetchPrimeId() {
        return SupplyUnitId;
    }

    @Override
    public void pushPrimeId(Object value) {
        SupplyUnitId = value.toString();
    }

    @Override
    public Object fetchParentId() {
        return null;
    }
}
