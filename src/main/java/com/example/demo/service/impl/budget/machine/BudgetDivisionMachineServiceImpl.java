package com.example.demo.service.impl.budget.machine;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.ProjectIndex;
import com.example.demo.entity.budget.machine.BudgetDivisionMachine;
import com.example.demo.mapper.budget.BudgetDivisionMapper;
import com.example.demo.mapper.budget.machine.BudgetDivisionMachineMapper;
import com.example.demo.service.IMyService;
import com.example.demo.service.budget.machine.IBudgetDivisionMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BudgetDivisionMachineServiceImpl
        extends ServiceImpl<BudgetDivisionMachineMapper, BudgetDivisionMachine>
        implements IBudgetDivisionMachineService {
    @Autowired
    BudgetDivisionMapper budgetDivisionMapper;

    @Override
    public String checkCanDelete(BudgetDivisionMachine instan) {

        QueryWrapper wrapper = new QueryWrapper<ProjectIndex>();
        wrapper.eq("parent_id", instan.fetchPrimeId());
        wrapper.last("limit 1");
        BudgetDivisionMachine child = getOne(wrapper);

        if (child != null) {
            return "删除错误：可能是存在子项，或者存在数据";
        }
        return null;
    }

    @Transactional
    @Override
    public boolean updateById(BudgetDivisionMachine entity) {
        super.updateById(entity);
        return IMyService.updateMachineWith(entity, budgetDivisionMapper, this.getBaseMapper(), "budget");
    }

    @Transactional
    @Override
    public boolean save(BudgetDivisionMachine entity) {
//        try {
        super.save(entity);

        return IMyService.updateMachineWith(entity, budgetDivisionMapper, this.getBaseMapper(), "budget");
//        } catch (Exception e) {
//            throw new RuntimeException();
//        }
    }

    @Transactional
    @Override
    public boolean removeById(BudgetDivisionMachine entity) {
        super.removeById(entity);
        return IMyService.updateMachineWith(entity, budgetDivisionMapper, this.getBaseMapper(), "budget");
    }
}
