package com.example.demo.entity.budget;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.BaseReport;
import com.example.demo.service.common.ISumReportService;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetDivision extends BaseReport implements ISumReportService {
    @TableId(type = IdType.INPUT)
    private String divisionId;


    private BigDecimal have;
  
    private BigDecimal manageUnitprice;
    private BigDecimal profitUnitprice;
    private BigDecimal manageSumprice;
    private BigDecimal profitSumprice;


    @TableField(exist = false)
    private String projectName;

    @Override
    public Object fetchPrimeId() {
        return divisionId;
    }

    @Override
    public void pushPrimeId(Object value) {
        divisionId = value.toString();
    }


    @Override
    public void pushWorkAmount(BigDecimal value) {
        if (value == null) {
            setWorkAmount(new BigDecimal(0));
        } else setWorkAmount(value);
    }

    @Override
    public void pushCostUnitprice(BigDecimal value) {
        if (value == null) {
            setCostUnitprice(new BigDecimal(0));
        } else setCostUnitprice(value);
    }

    ;

    @Override
    public void pushCostSumprice(BigDecimal value) {
        if (value == null) {
            setCostSumprice(new BigDecimal(0));
        } else setCostSumprice(value);
    }

    @Override
    public BigDecimal fetchWorkAmount() {
        return getWorkAmount() == null ? new BigDecimal(0) : getWorkAmount();
    }

    ;

    @Override
    public BigDecimal fetchCostUnitprice() {
        return getCostUnitprice() == null ? new BigDecimal(0) : getCostUnitprice();
    }

    ;

    @Override
    public BigDecimal fetchCostSumprice() {
        return getCostSumprice() == null ? new BigDecimal(0) : getCostSumprice();
    }
}
