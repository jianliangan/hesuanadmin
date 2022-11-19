package com.example.demo.entity.actual.machine;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.demo.entity.BaseMachine;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ActualDivisionMachine extends BaseMachine {

    private BigDecimal have;

    private BigDecimal taxRate;
    private BigDecimal referenceValue;

    private Integer tag;
    private String subPackageId;
    private String supplyUnitId;

    @TableField(exist = false)
    private String supplyUnitName;

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
