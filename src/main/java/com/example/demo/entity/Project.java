package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Project {
    @TableId(type= IdType.AUTO)
    private Integer projectId;
    private String projectName;
    private String province;
    private String city;
    private String region;
    private String startTime;

    private String completeTime;
    private String username;
    private String nature;
    private String category;
    private String categoryDetail;

    private String status;
    private String contractPrice;
    private String finalTime;
    private String estimateIncome;
    private String estimateCost;
    private String taxWay;
    @TableField(exist = false)
    private String cmd;

}
