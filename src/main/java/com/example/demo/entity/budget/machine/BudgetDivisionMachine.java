package com.example.demo.entity.budget.machine;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.demo.entity.BaseMachine;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetDivisionMachine extends BaseMachine {

    private BigDecimal loss;
    private BigDecimal have;

    private Integer tag;
    private String subPackageId;

    @TableField(exist = false)
    private String subPackageName;

    @Override
    public Object fetchPrimeId() {
        return id;
    }

    @Override
    public void pushPrimeId(Object value) {
        id = value.toString();
    }

}
