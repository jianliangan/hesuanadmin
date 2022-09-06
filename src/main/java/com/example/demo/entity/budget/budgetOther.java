package com.example.demo.entity.budget;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;

public class budgetOther {
    @TableId(type= IdType.AUTO)
    private String otherId;
    private String name;
    private BigDecimal divisionManageCost;
    private BigDecimal measureManageCost;
    private BigDecimal inProfessManageCost;
    private BigDecimal inLabourManageCost;
    private BigDecimal total;
}
