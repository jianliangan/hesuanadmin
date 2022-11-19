package com.example.demo.entity.plan.machine;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.demo.entity.BaseMachine;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlanDivisionMachine extends BaseMachine {


    private BigDecimal have;

    private BigDecimal taxRate;
    private BigDecimal referenceValue;


    private String subPackageId;
    private String supplyUnitId;
    private Integer tag;
    @TableField(exist = false)
    private String supplyUnitName;

    @TableField(exist = false)
    private String subPackageName;
    //  @Override
    //  public BigDecimal getCount() {
    //    return count;
    //  }
    //
    //  @Override
    //  public BigDecimal getPrice() {
    //    return price;
    //  }
    //
    //  @Override
    //  public BigDecimal getCombinedPrice() {
    //    return combinedPrice;
    //  }

    @Override
    public Object fetchPrimeId() {
        return id;
    }

    @Override
    public void pushPrimeId(Object value) {
        id = value.toString();
    }


}
