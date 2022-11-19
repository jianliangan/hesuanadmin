package com.example.demo.entity.budget.machine;

import com.example.demo.entity.BaseMachine;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetMeasureMachine extends BaseMachine {

    private BigDecimal loss;
    private BigDecimal have;
    private Integer tag;

    @Override
    public Object fetchPrimeId() {
        return id;
    }

    @Override
    public void pushPrimeId(Object value) {
        id = value.toString();
    }

}
