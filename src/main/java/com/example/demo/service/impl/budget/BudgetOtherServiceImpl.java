package com.example.demo.service.impl.budget;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.budget.BudgetOther;
import com.example.demo.mapper.budget.BudgetOtherMapper;
import com.example.demo.service.budget.IBudgetOtherService;
import org.springframework.stereotype.Service;

@Service
public class BudgetOtherServiceImpl extends ServiceImpl<BudgetOtherMapper, BudgetOther>
    implements IBudgetOtherService {
  @Override
  public boolean checkCanDelete(BudgetOther instan) {
    // log.info("存储数据库成功！{}", instan);
    return true;
  }
}
