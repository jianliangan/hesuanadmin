package com.example.demo.service.impl.actual.machine;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.actual.machine.ActualDivisionMachine;
import com.example.demo.mapper.actual.ActualDivisionMapper;
import com.example.demo.mapper.actual.machine.ActualDivisionMachineMapper;
import com.example.demo.service.IMyService;
import com.example.demo.service.actual.machine.IActualDivisionMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActualDivisionMachineServiceImpl
        extends ServiceImpl<ActualDivisionMachineMapper, ActualDivisionMachine>
        implements IActualDivisionMachineService {
    @Override
    public String checkCanDelete(ActualDivisionMachine instan) {

        QueryWrapper wrapper = new QueryWrapper<ActualDivisionMachine>();
        wrapper.eq("parent_id", instan.fetchPrimeId());
        wrapper.last("limit 1");
        ActualDivisionMachine child = getOne(wrapper);

        if (child != null) {
            return "删除错误：可能是存在子项，或者存在数据";
        }
        return null;
    }

    @Autowired
    ActualDivisionMapper actualDivisionMapper;

    @Transactional
    @Override
    public boolean updateById(ActualDivisionMachine entity) {
        super.updateById(entity);
        return IMyService.updateMachineWith(entity, actualDivisionMapper, this.getBaseMapper(), "actual");
    }

    @Transactional
    @Override
    public boolean save(ActualDivisionMachine entity) {

        super.save(entity);
        return IMyService.updateMachineWith(entity, actualDivisionMapper, this.getBaseMapper(), "actual");
    }

    @Transactional
    @Override
    public boolean removeById(ActualDivisionMachine entity) {
        super.removeById(entity);
        return IMyService.updateMachineWith(entity, actualDivisionMapper, this.getBaseMapper(), "actual");
    }
}
