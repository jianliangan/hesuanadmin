package com.example.demo.entity.budget;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.BaseReport;
import com.example.demo.service.common.ISumReportService;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetMeasure extends BaseReport implements ISumReportService {
    @TableId(type = IdType.INPUT)
    private String measureId;

    private BigDecimal have;
  
    private BigDecimal manageUnitprice;
    private BigDecimal profitUnitprice;
    private BigDecimal manageSumprice;
    private BigDecimal profitSumprice;


    @TableField(exist = false)
    private String projectName;

    @Override
    public Object fetchPrimeId() {
        return measureId;
    }

    @Override
    public void pushPrimeId(Object value) {
        measureId = value.toString();
    }


    @Override
    public void pushWorkAmount(BigDecimal value) {
        setWorkAmount(value);
    }

    @Override
    public void pushCostUnitprice(BigDecimal value) {
        setCostUnitprice(value);
    }

    ;

    @Override
    public void pushCostSumprice(BigDecimal value) {
        setCostSumprice(value);
    }

    @Override
    public BigDecimal fetchWorkAmount() {
        return getWorkAmount();
    }

    ;

    @Override
    public BigDecimal fetchCostUnitprice() {
        return getCostUnitprice();
    }

    ;

    @Override
    public BigDecimal fetchCostSumprice() {
        return getCostSumprice();
    }
}
