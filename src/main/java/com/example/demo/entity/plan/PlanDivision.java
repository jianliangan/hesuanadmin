package com.example.demo.entity.plan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.PlanAll;
import com.example.demo.service.common.ISumReportService;
import lombok.Data;

@Data
public class PlanDivision extends PlanAll implements ISumReportService {
    @TableId(type = IdType.INPUT)
    private String divisionId;

    @Override
    public Object fetchPrimeId() {
        return divisionId;
    }

    @Override
    public void pushPrimeId(Object value) {
        divisionId = value.toString();
    }
}
