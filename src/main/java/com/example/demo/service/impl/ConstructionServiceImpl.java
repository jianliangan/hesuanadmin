package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Construction;
import com.example.demo.mapper.ConstructionMapper;
import com.example.demo.service.IConstructionService;
import org.springframework.stereotype.Service;

@Service
public class ConstructionServiceImpl extends ServiceImpl<ConstructionMapper, Construction>
    implements IConstructionService {
  @Override
  public boolean checkCanDelete(Construction instan) {
    // log.info("存储数据库成功！{}", instan);
    return true;
  }
}
