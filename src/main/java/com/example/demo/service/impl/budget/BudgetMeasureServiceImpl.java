package com.example.demo.service.impl.budget;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.budget.BudgetMeasure;
import com.example.demo.mapper.budget.BudgetMeasureMapper;
import com.example.demo.service.budget.IBudgetMeasureService;
import org.springframework.stereotype.Service;

@Service
public class BudgetMeasureServiceImpl extends ServiceImpl<BudgetMeasureMapper, BudgetMeasure>
    implements IBudgetMeasureService {
  @Override
  public boolean checkCanDelete(BudgetMeasure instan) {
    // log.info("存储数据库成功！{}", instan);
    return true;
  }
}
