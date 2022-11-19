package com.example.demo.service.impl.actual;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.actual.ActualMeasure;
import com.example.demo.mapper.actual.ActualMeasureMapper;
import com.example.demo.service.actual.IActualMeasureService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ActualMeasureServiceImpl extends ServiceImpl<ActualMeasureMapper, ActualMeasure>
        implements IActualMeasureService {


    public ActualMeasure getPrimeOne(Object key) {
        QueryWrapper wrapper = new QueryWrapper();
        Map wheres = new HashMap<String, String>();
        wheres.put("measure_id", key);
        wrapper.allEq(wheres);
        return super.getOne(wrapper);
    }

    @Override
    public String checkCanDelete(ActualMeasure instan) {
        QueryWrapper wrapper = new QueryWrapper<ActualMeasure>();
        wrapper.eq("parent_id", instan.fetchPrimeId());
        wrapper.last("limit 1");
        ActualMeasure child = getOne(wrapper);

        if (child != null) {
            return "删除错误：可能是存在子项，或者存在数据";
        }
        return null;
    }

}
