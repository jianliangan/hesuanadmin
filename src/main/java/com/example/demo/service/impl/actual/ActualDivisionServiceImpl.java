package com.example.demo.service.impl.actual;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.ListInventory;
import com.example.demo.entity.actual.ActualDivision;
import com.example.demo.mapper.ListInventoryMapper;
import com.example.demo.mapper.actual.ActualDivisionMapper;
import com.example.demo.service.actual.IActualDivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ActualDivisionServiceImpl extends ServiceImpl<ActualDivisionMapper, ActualDivision>
        implements IActualDivisionService {

    @Autowired
    ListInventoryMapper listInventoryMapper;


    public ActualDivision getPrimeOne(Object key) {
        QueryWrapper wrapper = new QueryWrapper();
        Map wheres = new HashMap<String, String>();
        wheres.put("division_id", key);
        wrapper.allEq(wheres);
        return super.getOne(wrapper);
    }

    @Override
    public String checkCanDelete(ActualDivision instan) {

        QueryWrapper wrapper = new QueryWrapper<ActualDivision>();
        wrapper.eq("parent_id", instan.fetchPrimeId());
        wrapper.last("limit 1");
        ActualDivision child = getOne(wrapper);

        if (child != null) {
            return "删除错误：可能是存在子项，或者存在数据";
        }
        return null;
    }

    private void extendInventory(ActualDivision entity) {
        ListInventory listInventory = new ListInventory();
        listInventory.setTableId(entity.getDivisionId());
        listInventory.setTableType("shiji");
        listInventoryMapper.insert(listInventory);
    }

    @Override
    public boolean updateById(ActualDivision entity) {
        extendInventory(entity);
        return IActualDivisionService.super.updateById(entity);
    }

    @Override
    public boolean save(ActualDivision entity) {
        extendInventory(entity);
        return IActualDivisionService.super.save(entity);
    }

}
