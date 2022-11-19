package com.example.demo.service.impl.budget.machine;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.budget.machine.BudgetMeasureMachine;
import com.example.demo.mapper.budget.BudgetMeasureMapper;
import com.example.demo.mapper.budget.machine.BudgetMeasureMachineMapper;
import com.example.demo.service.IMyService;
import com.example.demo.service.budget.machine.IBudgetMeasureMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BudgetMeasureMachineServiceImpl
        extends ServiceImpl<BudgetMeasureMachineMapper, BudgetMeasureMachine>
        implements IBudgetMeasureMachineService {
    @Autowired
    BudgetMeasureMapper budgetMeasureMapper;

    @Transactional
    @Override
    public boolean updateById(BudgetMeasureMachine entity) {
        super.updateById(entity);
        return IMyService.updateMachineWith(entity, budgetMeasureMapper, this.getBaseMapper(), "budget");
    }

    @Transactional
    @Override
    public boolean save(BudgetMeasureMachine entity) {

        super.save(entity);
        return IMyService.updateMachineWith(entity, budgetMeasureMapper, this.getBaseMapper(), "budget");
    }

    @Transactional
    @Override
    public boolean removeById(BudgetMeasureMachine entity) {
        super.removeById(entity);
        return IMyService.updateMachineWith(entity, budgetMeasureMapper, this.getBaseMapper(), "budget");
    }
}
