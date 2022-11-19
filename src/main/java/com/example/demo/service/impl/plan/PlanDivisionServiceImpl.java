package com.example.demo.service.impl.plan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.ListInventory;
import com.example.demo.entity.plan.PlanDivision;
import com.example.demo.mapper.ListInventoryMapper;
import com.example.demo.mapper.plan.PlanDivisionMapper;
import com.example.demo.service.plan.IPlanDivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PlanDivisionServiceImpl extends ServiceImpl<PlanDivisionMapper, PlanDivision>
        implements IPlanDivisionService {

    @Autowired
    ListInventoryMapper listInventoryMapper;

    public PlanDivision getPrimeOne(Object key) {
        QueryWrapper wrapper = new QueryWrapper();
        Map wheres = new HashMap<String, String>();
        wheres.put("division_id", key);
        wrapper.allEq(wheres);
        return super.getOne(wrapper);
    }

    @Override
    public String checkCanDelete(PlanDivision instan) {

        QueryWrapper wrapper = new QueryWrapper<PlanDivision>();
        wrapper.eq("parent_id", instan.fetchPrimeId());
        wrapper.last("limit 1");
        PlanDivision child = getOne(wrapper);

        if (child != null) {
            return "删除错误：可能是存在子项，或者存在数据";
        }
        return null;
    }

    private void extendInventory(PlanDivision entity) {
        ListInventory listInventory = new ListInventory();
        listInventory.setTableId(entity.getDivisionId());
        listInventory.setTableType("jiahua");
        listInventoryMapper.insert(listInventory);
    }

    @Override
    public boolean updateById(PlanDivision entity) {
        extendInventory(entity);
        return IPlanDivisionService.super.updateById(entity);
    }

    @Override
    public boolean save(PlanDivision entity) {
        extendInventory(entity);
        return IPlanDivisionService.super.save(entity);
    }


}
