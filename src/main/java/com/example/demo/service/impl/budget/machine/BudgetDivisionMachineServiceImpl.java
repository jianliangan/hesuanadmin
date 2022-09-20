package com.example.demo.service.impl.budget.machine;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.ProjectIndex;
import com.example.demo.entity.budget.machine.BudgetDivisionMachine;
import com.example.demo.mapper.budget.machine.BudgetDivisionMachineMapper;
import com.example.demo.service.budget.machine.IBudgetDivisionMachineService;
import org.springframework.stereotype.Service;

@Service
public class BudgetDivisionMachineServiceImpl
    extends ServiceImpl<BudgetDivisionMachineMapper, BudgetDivisionMachine>
    implements IBudgetDivisionMachineService {
  @Override
  public boolean checkCanDelete(BudgetDivisionMachine instan) {

    QueryWrapper wrapper = new QueryWrapper<ProjectIndex>();
    wrapper.eq("parent_id", instan.fetchPrimeId());
    wrapper.last("limit 1");
    BudgetDivisionMachine child = getOne(wrapper);

    if (child != null) {
      return false;
    }
    return true;
  }
}
