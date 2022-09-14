package com.example.demo.service.impl.budget;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.budget.BudgetDivision;
import com.example.demo.mapper.budget.BudgetDivisionMapper;
import com.example.demo.service.budget.IBudgetDivisionService;
import org.springframework.stereotype.Service;

@Service
public class BudgetDivisionServiceImpl extends ServiceImpl<BudgetDivisionMapper, BudgetDivision>
    implements IBudgetDivisionService {
  @Override
  public boolean checkCanDelete(BudgetDivision instan) {
    // log.info("存储数据库成功！{}", instan);
    return true;
  }
}
