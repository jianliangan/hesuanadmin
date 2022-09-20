package com.example.demo.service.impl.budget;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.ProjectIndex;
import com.example.demo.entity.budget.BudgetDivision;
import com.example.demo.mapper.budget.BudgetDivisionMapper;
import com.example.demo.service.budget.IBudgetDivisionService;
import org.springframework.stereotype.Service;

@Service
public class BudgetDivisionServiceImpl extends ServiceImpl<BudgetDivisionMapper, BudgetDivision>
    implements IBudgetDivisionService {
  @Override
  public boolean checkCanDelete(BudgetDivision instan) {

    QueryWrapper wrapper = new QueryWrapper<ProjectIndex>();
    wrapper.eq("parent_id", instan.fetchPrimeId());
    wrapper.last("limit 1");
    BudgetDivision child = getOne(wrapper);

    if (child != null) {
      return false;
    }
    return true;
  }
}
