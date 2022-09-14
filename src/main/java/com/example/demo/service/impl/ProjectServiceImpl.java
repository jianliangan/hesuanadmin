package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Project;
import com.example.demo.mapper.ProjectMapper;
import com.example.demo.service.IProjectService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>
    implements IProjectService {
  @Override
  public boolean checkCanDelete(Project instan) {
    // log.info("存储数据库成功！{}", instan);
    return true;
  }
}
