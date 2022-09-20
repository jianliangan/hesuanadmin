package com.example.demo.service.impl.budget.machine;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.budget.machine.BudgetMeasureMachine;
import com.example.demo.mapper.budget.machine.BudgetMeasureMachineMapper;
import com.example.demo.service.budget.machine.IBudgetMeasureMachineService;
import org.springframework.stereotype.Service;

@Service
public class BudgetMeasureMachineServiceImpl
    extends ServiceImpl<BudgetMeasureMachineMapper, BudgetMeasureMachine>
    implements IBudgetMeasureMachineService {
  @Override
  public boolean checkCanDelete(BudgetMeasureMachine instan) {
    // log.info("存储数据库成功！{}", instan);
    return true;
  }
}
