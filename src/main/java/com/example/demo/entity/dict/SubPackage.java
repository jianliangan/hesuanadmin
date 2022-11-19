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
public class SubPackage extends Base {
    @TableId(type = IdType.INPUT)
    private String subPackageId;
    @Length(max = 50, message = "名称长度不能超过50")
    private String subPackageName;
    @Length(max = 50, message = "联系方式长度不能超过50")
    private String contact;
    @Length(max = 50, message = "手机号长度不能超过50")
    private String phone;
    private String manager;
    @TableField(exist = false)
    private String managerName;
    private Date datetime;
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
