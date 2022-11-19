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
public class Materials extends Base {
    @TableId(type = IdType.INPUT)
    private String materialsId;
    @Length(max = 50, message = "材料名称不能超过50")
    private String materialsName;
    @Length(max = 50, message = "编码长度不能超过50")
    private String code;
    @Length(max = 50, message = "类型长度不能超过50")
    private String category;
    @Length(max = 50, message = "科目长度不能超过50")
    private String subject;
    @Length(max = 800, message = "特征长度不能超过800")
    private String distinction;
    @Length(max = 50, message = "单位长度不能超过50")
    private String unit;
    @Length(max = 20, message = "状态长度不能超过20")
    private String status;
    private String delFlag;
    private String createBy;
    @TableField(exist = false)
    private String createByName;
    private Date createTime;
    private String schedule;
    
    private Date updateTime;

    private String remark;
    private String by1;
    private String by2;
    private String by3;
    private String by4;

    private BigDecimal sort;
    private String ownId;
    private String parentId;
    private Integer tag;

    @Override
    public Object fetchPrimeId() {
        return materialsId;
    }

    @Override
    public void pushPrimeId(Object value) {
        materialsId = value.toString();
    }

    @Override
    public Object fetchParentId() {
        return null;
    }
}
