package com.example.demo.service.impl.plan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.plan.PlanMeasure;
import com.example.demo.mapper.plan.PlanMeasureMapper;
import com.example.demo.service.plan.IPlanMeasureService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PlanMeasureServiceImpl extends ServiceImpl<PlanMeasureMapper, PlanMeasure>
        implements IPlanMeasureService {

    public PlanMeasure getPrimeOne(Object key) {
        QueryWrapper wrapper = new QueryWrapper();
        Map wheres = new HashMap<String, String>();
        wheres.put("measure_id", key);
        wrapper.allEq(wheres);
        return super.getOne(wrapper);
    }

    @Override
    public String checkCanDelete(PlanMeasure instan) {
        QueryWrapper wrapper = new QueryWrapper<PlanMeasure>();
        wrapper.eq("parent_id", instan.fetchPrimeId());
        wrapper.last("limit 1");
        PlanMeasure child = getOne(wrapper);

        if (child != null) {
            return "删除错误：可能是存在子项，或者存在数据";
        }
        return null;
    }

}
