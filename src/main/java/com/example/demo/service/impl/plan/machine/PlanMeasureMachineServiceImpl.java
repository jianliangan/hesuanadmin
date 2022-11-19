package com.example.demo.service.impl.plan.machine;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.plan.machine.PlanMeasureMachine;
import com.example.demo.mapper.plan.PlanMeasureMapper;
import com.example.demo.mapper.plan.machine.PlanMeasureMachineMapper;
import com.example.demo.service.IMyService;
import com.example.demo.service.plan.machine.IPlanMeasureMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanMeasureMachineServiceImpl
        extends ServiceImpl<PlanMeasureMachineMapper, PlanMeasureMachine>
        implements IPlanMeasureMachineService {
    @Autowired
    PlanMeasureMapper planMeasureMapper;

    @Transactional
    @Override
    public boolean updateById(PlanMeasureMachine entity) {
        super.updateById(entity);
        return IMyService.updateMachineWith(entity, planMeasureMapper, this.getBaseMapper(), "plan");
    }

    @Transactional
    @Override
    public boolean save(PlanMeasureMachine entity) {

        super.save(entity);
        return IMyService.updateMachineWith(entity, planMeasureMapper, this.getBaseMapper(), "plan");
    }

    @Transactional
    @Override
    public boolean removeById(PlanMeasureMachine entity) {
        super.removeById(entity);
        return IMyService.updateMachineWith(entity, planMeasureMapper, this.getBaseMapper(), "plan");
    }
}
