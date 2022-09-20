package com.example.demo.service.impl.plan.machine;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.plan.machine.PlanDivisionMachine;
import com.example.demo.mapper.plan.machine.PlanDivisionMachineMapper;
import com.example.demo.service.plan.machine.IPlanDivisionMachineService;
import org.springframework.stereotype.Service;

@Service
public class PlanDivisionMachineServiceImpl
    extends ServiceImpl<PlanDivisionMachineMapper, PlanDivisionMachine>
    implements IPlanDivisionMachineService {
  @Override
  public boolean checkCanDelete(PlanDivisionMachine instan) {

    QueryWrapper wrapper = new QueryWrapper<PlanDivisionMachine>();
    wrapper.eq("parent_id", instan.fetchPrimeId());
    wrapper.last("limit 1");
    PlanDivisionMachine child = getOne(wrapper);

    if (child != null) {
      return false;
    }
    return true;
  }
}
