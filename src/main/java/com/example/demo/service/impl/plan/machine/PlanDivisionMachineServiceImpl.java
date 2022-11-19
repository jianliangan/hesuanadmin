package com.example.demo.service.impl.plan.machine;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.plan.machine.PlanDivisionMachine;
import com.example.demo.mapper.plan.PlanDivisionMapper;
import com.example.demo.mapper.plan.machine.PlanDivisionMachineMapper;
import com.example.demo.service.IMyService;
import com.example.demo.service.plan.machine.IPlanDivisionMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanDivisionMachineServiceImpl
        extends ServiceImpl<PlanDivisionMachineMapper, PlanDivisionMachine>
        implements IPlanDivisionMachineService {
    @Override
    public String checkCanDelete(PlanDivisionMachine instan) {

        QueryWrapper wrapper = new QueryWrapper<PlanDivisionMachine>();
        wrapper.eq("parent_id", instan.fetchPrimeId());
        wrapper.last("limit 1");
        PlanDivisionMachine child = getOne(wrapper);

        if (child != null) {
            return "删除错误：可能是存在子项，或者存在数据";
        }
        return null;
    }

    @Autowired
    PlanDivisionMapper planDivisionMapper;

    @Transactional
    @Override
    public boolean updateById(PlanDivisionMachine entity) {
        super.updateById(entity);
        return IMyService.updateMachineWith(entity, planDivisionMapper, this.getBaseMapper(), "plan");
    }

    @Transactional
    @Override
    public boolean save(PlanDivisionMachine entity) {

        super.save(entity);
        return IMyService.updateMachineWith(entity, planDivisionMapper, this.getBaseMapper(), "plan");
    }

    @Transactional
    @Override
    public boolean removeById(PlanDivisionMachine entity) {
        super.removeById(entity);
        return IMyService.updateMachineWith(entity, planDivisionMapper, this.getBaseMapper(), "plan");
    }
}
