package com.example.demo.service.impl.actual.machine;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.actual.machine.ActualDivisionMachine;
import com.example.demo.mapper.actual.machine.ActualDivisionMachineMapper;
import com.example.demo.service.actual.machine.IActualDivisionMachineService;
import org.springframework.stereotype.Service;

@Service
public class ActualDivisionMachineServiceImpl
    extends ServiceImpl<ActualDivisionMachineMapper, ActualDivisionMachine>
    implements IActualDivisionMachineService {
  @Override
  public boolean checkCanDelete(ActualDivisionMachine instan) {

    QueryWrapper wrapper = new QueryWrapper<ActualDivisionMachine>();
    wrapper.eq("parent_id", instan.fetchPrimeId());
    wrapper.last("limit 1");
    ActualDivisionMachine child = getOne(wrapper);

    if (child != null) {
      return false;
    }
    return true;
  }
}
