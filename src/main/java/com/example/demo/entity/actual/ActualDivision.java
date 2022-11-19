package com.example.demo.entity.actual;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.PlanAll;
import com.example.demo.service.common.ISumReportService;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ActualDivision extends PlanAll implements ISumReportService {
    @TableId(type = IdType.INPUT)
    private String divisionId;
    private BigDecimal budgetWorkAmount;

    @Override
    public String toString() {
        return "ActualDivision{" +
                "divisionId='" + divisionId + '\'' +


                ", budgetWorkAmount=" + budgetWorkAmount +

                ", projectName='" + projectName + '\'' +
                "} " + super.toString();
    }

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

}
