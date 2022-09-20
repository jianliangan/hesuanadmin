package com.example.demo.service.impl.budget;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.ProjectIndex;
import com.example.demo.entity.budget.BudgetMeasure;
import com.example.demo.mapper.budget.BudgetMeasureMapper;
import com.example.demo.service.budget.IBudgetMeasureService;
import org.springframework.stereotype.Service;

@Service
public class BudgetMeasureServiceImpl extends ServiceImpl<BudgetMeasureMapper, BudgetMeasure>
    implements IBudgetMeasureService {
  @Override
  public boolean checkCanDelete(BudgetMeasure instan) {
    QueryWrapper wrapper = new QueryWrapper<ProjectIndex>();
    wrapper.eq("parent_id", instan.fetchPrimeId());
    wrapper.last("limit 1");
    BudgetMeasure child = getOne(wrapper);

    if (child != null) {
      return false;
    }
    return true;
  }
}
