package com.example.demo.entity.budget;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class budgetDivision {
    @TableId(type= IdType.AUTO)

       private String subject;
    private String code;
    private String category;
    private String name;
    private String distinction;
    private String unit;
    private BigDecimal have;
    private BigDecimal work_amount;
    private BigDecimal synthesisUnitprice;
    private BigDecimal synthesisSumprice;
    private BigDecimal manageUnitprice;
    private BigDecimal profitUnitprice;
    private BigDecimal manageSumprice;
    private BigDecimal profitSumprice;
}
