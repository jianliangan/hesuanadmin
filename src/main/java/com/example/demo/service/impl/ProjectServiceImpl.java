package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Project;
import com.example.demo.entity.ProjectIndex;
import com.example.demo.mapper.ProjectMapper;
import com.example.demo.service.IProjectService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>
    implements IProjectService {
  @Override
  public boolean checkCanDelete(Project instan) {

    QueryWrapper wrapper = new QueryWrapper<ProjectIndex>();
    wrapper.eq("parent_id", instan.fetchPrimeId());
    wrapper.last("limit 1");
    Project child = getOne(wrapper);

    if (child != null) {
      return false;
    }
    return true;
  }
}
