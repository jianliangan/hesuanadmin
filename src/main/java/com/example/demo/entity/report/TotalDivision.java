package com.example.demo.entity.report;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.entity.BaseReport;
import com.example.demo.service.common.ISumReportService;
import lombok.Data;

import java.math.BigDecimal;

@TableName("total_division")
@Data
public class TotalDivision extends BaseReport implements ISumReportService {
    @TableId(type = IdType.INPUT)
    private String divisionId;

    private BigDecimal have;

    private BigDecimal budgetWorkAmount;
    private BigDecimal budgetCostUnitprice;
    private BigDecimal budgetCostSumprice;
    private BigDecimal budgetManageUnitprice;
    private BigDecimal budgetProfitUnitprice;
    private BigDecimal budgetManageSumprice;
    private BigDecimal budgetProfitSumprice;

    private BigDecimal planWorkAmount;
    private BigDecimal planCostUnitprice;
    private BigDecimal planCostSumprice;
    private BigDecimal planCostManprice;
    private BigDecimal planCostMaterialsprice;
    private BigDecimal planCostMechanicsprice;
    private BigDecimal planCostDeviceprice;
    private BigDecimal planCostSubpackageprice;

    private BigDecimal actualWorkAmount;
    private BigDecimal actualCostUnitprice;
    private BigDecimal actualCostSumprice;
    private BigDecimal actualCostManprice;
    private BigDecimal actualCostMaterialsprice;
    private BigDecimal actualCostMechanicsprice;
    private BigDecimal actualCostDeviceprice;
    private BigDecimal actualCostSubpackageprice;


    @TableField(exist = false)
    private String projectName = "";

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
        //    if (value == null) {
        //      setBudgetWorkAmount(new BigDecimal(0));
        //    } else setBudgetWorkAmount(value);
    }

    @Override
    public void pushCostUnitprice(BigDecimal value) {
        //    if (value == null) {
        //      setBudgetCostUnitprice(new BigDecimal(0));
        //    } else setBudgetCostUnitprice(value);
    }

    ;

    @Override
    public void pushCostSumprice(BigDecimal value) {
        //    if (value == null) {
        //      setBudgetCostSumprice(new BigDecimal(0));
        //    } else setBudgetCostSumprice(value);
    }

    @Override
    public BigDecimal fetchWorkAmount() {
        //    return getBudgetWorkAmount() == null ? new BigDecimal(0) : getBudgetWorkAmount();
        return new BigDecimal(0);
    }

    ;

    @Override
    public BigDecimal fetchCostUnitprice() {
        //    return getBudgetCostUnitprice() == null
        //        ? new BigDecimal(0)
        //        : getBudgetCostUnitprice();
        return new BigDecimal(0);
    }

    ;

    @Override
    public BigDecimal fetchCostSumprice() {
        //    return getBudgetCostSumprice() == null ? new BigDecimal(0) :
        // getBudgetCostSumprice();
        return new BigDecimal(0);
    }
}
