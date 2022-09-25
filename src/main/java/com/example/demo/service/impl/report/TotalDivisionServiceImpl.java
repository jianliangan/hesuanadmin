package com.example.demo.service.impl.report;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.report.TotalDivision;
import com.example.demo.mapper.report.TotalDivisionMapper;
import com.example.demo.service.report.ITotalDivisionService;
import org.springframework.stereotype.Service;

@Service
public class TotalDivisionServiceImpl extends ServiceImpl<TotalDivisionMapper, TotalDivision>
    implements ITotalDivisionService {
  @Override
  public boolean checkCanDelete(TotalDivision instan) {

    return true;
  }
}
