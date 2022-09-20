package com.example.demo.service.impl.plan.machine;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.plan.machine.PlanMeasureMachine;
import com.example.demo.mapper.plan.machine.PlanMeasureMachineMapper;
import com.example.demo.service.plan.machine.IPlanMeasureMachineService;
import org.springframework.stereotype.Service;

@Service
public class PlanMeasureMachineServiceImpl
    extends ServiceImpl<PlanMeasureMachineMapper, PlanMeasureMachine>
    implements IPlanMeasureMachineService {
  @Override
  public boolean checkCanDelete(PlanMeasureMachine instan) {
    // log.info("存储数据库成功！{}", instan);
    return true;
  }
}
