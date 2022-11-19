package com.example.demo.entity.plan.machine;

import com.example.demo.entity.BaseMachine;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlanMeasureMachine extends BaseMachine {

    private BigDecimal have;

    private BigDecimal taxRate;
    private BigDecimal referenceValue;

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
