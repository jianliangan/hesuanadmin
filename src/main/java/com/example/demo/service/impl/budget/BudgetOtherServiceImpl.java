package com.example.demo.service.impl.budget;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.budget.BudgetOther;
import com.example.demo.mapper.budget.BudgetOtherMapper;
import com.example.demo.service.IMyService;
import com.example.demo.service.budget.IBudgetOtherService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BudgetOtherServiceImpl extends ServiceImpl<BudgetOtherMapper, BudgetOther>
        implements IBudgetOtherService {
   

    public String save2update(BudgetOther entity, IMyService<BudgetOther> ss) {

        QueryWrapper wrapper = new QueryWrapper();
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("name", entity.getName() == null ? "" : entity.getName());
        wrapper.allEq(stringMap);

        BudgetOther instance = getOne(wrapper);
        if (instance != null) {
            entity.pushPrimeId(instance.fetchPrimeId());
            entity.setOwnId(instance.getOwnId());
            entity.setParentId(instance.getParentId());
            entity.setTag(instance.getTag());
            entity.setSort(instance.getSort());
            updateById(entity);
        } else {
            save(entity);
        }
        return (String) entity.fetchPrimeId();
    }
}
