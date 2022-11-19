package com.example.demo.entity.actual;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.PlanAll;
import com.example.demo.service.common.ISumReportService;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ActualMeasure extends PlanAll implements ISumReportService {
    @TableId(type = IdType.INPUT)
    private String measureId;
    private BigDecimal budgetWorkAmount;

    @Override
    public Object fetchPrimeId() {
        return measureId;
    }

    @Override
    public void pushPrimeId(Object value) {
        measureId = value.toString();
    }

}
