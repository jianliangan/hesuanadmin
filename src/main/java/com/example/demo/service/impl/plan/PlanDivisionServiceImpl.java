package com.example.demo.service.impl.plan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.plan.PlanDivision;
import com.example.demo.mapper.plan.PlanDivisionMapper;
import com.example.demo.service.plan.IPlanDivisionService;
import org.springframework.stereotype.Service;

@Service
public class PlanDivisionServiceImpl extends ServiceImpl<PlanDivisionMapper, PlanDivision>
    implements IPlanDivisionService {
  @Override
  public boolean checkCanDelete(PlanDivision instan) {

    QueryWrapper wrapper = new QueryWrapper<PlanDivision>();
    wrapper.eq("parent_id", instan.fetchPrimeId());
    wrapper.last("limit 1");
    PlanDivision child = getOne(wrapper);

    if (child != null) {
      return false;
    }
    return true;
  }
}
