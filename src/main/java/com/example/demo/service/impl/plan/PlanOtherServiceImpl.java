package com.example.demo.service.impl.plan;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.plan.PlanOther;
import com.example.demo.mapper.plan.PlanOtherMapper;
import com.example.demo.service.plan.IPlanOtherService;
import org.springframework.stereotype.Service;

@Service
public class PlanOtherServiceImpl extends ServiceImpl<PlanOtherMapper, PlanOther>
    implements IPlanOtherService {
  @Override
  public boolean checkCanDelete(PlanOther instan) {
    // log.info("存储数据库成功！{}", instan);
    return true;
  }
}
