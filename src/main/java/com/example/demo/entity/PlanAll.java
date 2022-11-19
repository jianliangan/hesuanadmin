package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

@Data
public abstract class PlanAll extends BaseReport {


    private BigDecimal have;

    private BigDecimal budgetWorkAmount;

    private BigDecimal costManprice;
    private BigDecimal costMaterialsprice;
    private BigDecimal costMechanicsprice;
    private BigDecimal costDeviceprice;
    private BigDecimal costSubpackageprice;


    @TableField(exist = false)
    private String projectName;


    @Override
    public String toString() {
        return "PlanDivision{" +

                ", have=" + have +

                ", budgetWorkAmount=" + budgetWorkAmount +

                ", costManprice=" + costManprice +
                ", costMaterialsprice=" + costMaterialsprice +
                ", costMechanicsprice=" + costMechanicsprice +
                ", costDeviceprice=" + costDeviceprice +
                ", costSubpackageprice=" + costSubpackageprice +
                ", projectName='" + projectName + '\'' +
                "} " + super.toString();
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
