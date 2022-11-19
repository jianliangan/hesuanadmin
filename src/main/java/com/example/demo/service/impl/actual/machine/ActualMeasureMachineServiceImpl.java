package com.example.demo.service.impl.actual.machine;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.actual.machine.ActualMeasureMachine;
import com.example.demo.mapper.actual.ActualMeasureMapper;
import com.example.demo.mapper.actual.machine.ActualMeasureMachineMapper;
import com.example.demo.service.IMyService;
import com.example.demo.service.actual.machine.IActualMeasureMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActualMeasureMachineServiceImpl
        extends ServiceImpl<ActualMeasureMachineMapper, ActualMeasureMachine>
        implements IActualMeasureMachineService {
    @Autowired
    ActualMeasureMapper actualMeasureMapper;

    @Transactional
    @Override
    public boolean updateById(ActualMeasureMachine entity) {
        super.updateById(entity);
        return IMyService.updateMachineWith(entity, actualMeasureMapper, this.getBaseMapper(), "actual");
    }

    @Transactional
    @Override
    public boolean save(ActualMeasureMachine entity) {

        super.save(entity);
        return IMyService.updateMachineWith(entity, actualMeasureMapper, this.getBaseMapper(), "actual");
    }

    @Transactional
    @Override
    public boolean removeById(ActualMeasureMachine entity) {
        super.removeById(entity);
        return IMyService.updateMachineWith(entity, actualMeasureMapper, this.getBaseMapper(), "actual");
    }
}
