package com.example.demo.service.impl.plan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.plan.PlanMeasure;
import com.example.demo.mapper.plan.PlanMeasureMapper;
import com.example.demo.service.plan.IPlanMeasureService;
import org.springframework.stereotype.Service;

@Service
public class PlanMeasureServiceImpl extends ServiceImpl<PlanMeasureMapper, PlanMeasure>
    implements IPlanMeasureService {
  @Override
  public boolean checkCanDelete(PlanMeasure instan) {
    QueryWrapper wrapper = new QueryWrapper<PlanMeasure>();
    wrapper.eq("parent_id", instan.fetchPrimeId());
    wrapper.last("limit 1");
    PlanMeasure child = getOne(wrapper);

    if (child != null) {
      return false;
    }
    return true;
  }
}
