package com.example.demo.service.impl.dict;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.dict.SubPackage;
import com.example.demo.mapper.dict.SubPackageMapper;
import com.example.demo.service.dict.ISubPackageService;
import org.springframework.stereotype.Service;

@Service
public class SubPackageServiceImpl extends ServiceImpl<SubPackageMapper, SubPackage>
    implements ISubPackageService {
  @Override
  public boolean checkCanDelete(SubPackage instan) {

    QueryWrapper wrapper = new QueryWrapper<SubPackage>();
    wrapper.eq("parent_id", instan.fetchPrimeId());
    wrapper.last("limit 1");
    SubPackage child = getOne(wrapper);

    if (child != null) {
      return false;
    }
    return true;
  }
}
