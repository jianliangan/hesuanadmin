package com.example.demo.service.impl.actual.machine;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.actual.machine.ActualMeasureMachine;
import com.example.demo.mapper.actual.machine.ActualMeasureMachineMapper;
import com.example.demo.service.actual.machine.IActualMeasureMachineService;
import org.springframework.stereotype.Service;

@Service
public class ActualMeasureMachineServiceImpl
    extends ServiceImpl<ActualMeasureMachineMapper, ActualMeasureMachine>
    implements IActualMeasureMachineService {
  @Override
  public boolean checkCanDelete(ActualMeasureMachine instan) {
    // log.info("存储数据库成功！{}", instan);
    return true;
  }
}
