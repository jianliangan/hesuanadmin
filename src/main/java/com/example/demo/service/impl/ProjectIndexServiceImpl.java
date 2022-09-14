package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.ProjectIndex;
import com.example.demo.mapper.ProjectIndexMapper;
import com.example.demo.service.IProjectIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProjectIndexServiceImpl extends ServiceImpl<ProjectIndexMapper, ProjectIndex>
    implements IProjectIndexService {
  @Override
  public boolean checkCanDelete(ProjectIndex instan) {
    log.info("存储数据库成功！{}", instan);
    QueryWrapper wrapper = new QueryWrapper<ProjectIndex>();
    wrapper.eq("parent_id", instan.getPrimeId());
    wrapper.last("limit 1");
    ProjectIndex child = getOne(wrapper);

    if (child != null) {
      return false;
    }
    return true;
  }
}
