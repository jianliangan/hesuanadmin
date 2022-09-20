package com.example.demo.service.impl.actual;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.actual.ActualDivision;
import com.example.demo.mapper.actual.ActualDivisionMapper;
import com.example.demo.service.actual.IActualDivisionService;
import org.springframework.stereotype.Service;

@Service
public class ActualDivisionServiceImpl extends ServiceImpl<ActualDivisionMapper, ActualDivision>
    implements IActualDivisionService {
  @Override
  public boolean checkCanDelete(ActualDivision instan) {

    QueryWrapper wrapper = new QueryWrapper<ActualDivision>();
    wrapper.eq("parent_id", instan.fetchPrimeId());
    wrapper.last("limit 1");
    ActualDivision child = getOne(wrapper);

    if (child != null) {
      return false;
    }
    return true;
  }
}
