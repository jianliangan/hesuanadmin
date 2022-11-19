package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class Project extends Base {
    @TableId(type = IdType.INPUT)
    private String projectId;
    @Length(max = 50, message = "名称长度不能超过50")
    private String projectName;
    private String province;
    private String city;
    private String region;
    private Date startTime;

    private Date completeTime;
    @Length(max = 50, message = "业主长度不能超过50")
    private String username;
    @Length(max = 50, message = "业务性质长度不能超过50")
    private String nature;
    @Length(max = 50, message = "类别长度不能超过50")
    private String category;
    @Length(max = 250, message = "业务性质长度不能超过250")
    private String categoryDetail;

    private String status;
    private BigDecimal contractPrice;
    private Date finalTime;
    private BigDecimal estimateIncome;
    private BigDecimal estimateCost;
    private String taxWay;

    private String parentId;
    private Integer sort;
    private String ownId;

    @Override
    public Object fetchPrimeId() {
        return projectId;
    }

    @Override
    public void pushPrimeId(Object value) {
        projectId = value.toString();
    }

    @Override
    public Object fetchParentId() {
        return parentId;
    }
}
