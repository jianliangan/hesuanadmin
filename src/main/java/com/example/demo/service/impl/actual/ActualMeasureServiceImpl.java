package com.example.demo.service.impl.actual;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.actual.ActualMeasure;
import com.example.demo.mapper.actual.ActualMeasureMapper;
import com.example.demo.service.actual.IActualMeasureService;
import org.springframework.stereotype.Service;

@Service
public class ActualMeasureServiceImpl extends ServiceImpl<ActualMeasureMapper, ActualMeasure>
    implements IActualMeasureService {
  @Override
  public boolean checkCanDelete(ActualMeasure instan) {
    QueryWrapper wrapper = new QueryWrapper<ActualMeasure>();
    wrapper.eq("parent_id", instan.fetchPrimeId());
    wrapper.last("limit 1");
    ActualMeasure child = getOne(wrapper);

    if (child != null) {
      return false;
    }
    return true;
  }
}
