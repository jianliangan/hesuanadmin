package com.example.demo.service.impl.budget;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.ProjectIndex;
import com.example.demo.entity.budget.BudgetMeasure;
import com.example.demo.mapper.budget.BudgetMeasureMapper;
import com.example.demo.service.budget.IBudgetMeasureService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BudgetMeasureServiceImpl extends ServiceImpl<BudgetMeasureMapper, BudgetMeasure>
        implements IBudgetMeasureService {

    public BudgetMeasure getPrimeOne(Object key) {
        QueryWrapper wrapper = new QueryWrapper();
        Map wheres = new HashMap<String, String>();
        wheres.put("measure_id", key);
        wrapper.allEq(wheres);
        return super.getOne(wrapper);
    }

    @Override
    public String checkCanDelete(BudgetMeasure instan) {
        QueryWrapper wrapper = new QueryWrapper<ProjectIndex>();
        wrapper.eq("parent_id", instan.fetchPrimeId());
        wrapper.last("limit 1");
        BudgetMeasure child = getOne(wrapper);

        if (child != null) {
            return "删除错误：可能是存在子项，或者存在数据";
        }
        return null;
    }

}
