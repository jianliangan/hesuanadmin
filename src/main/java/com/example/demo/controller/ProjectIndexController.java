package com.example.demo.controller;

import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.Project;
import com.example.demo.service.IMyService;
import com.example.demo.service.IProjectService;
import com.example.demo.service.common.WrapperOpt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/projectindex")
public class ProjectIndexController extends BaseController<Project> {

  @Autowired private IProjectService projectService;

  @Override
  protected IMyService fetchService() {
    return projectService;
  }

  @Override
  protected String commonPrePushCheck(HttpServletRequest request) {
    return null;
  }

  @Override
  protected String commonPreFetchCheck(HttpServletRequest request) {
    int projectId =
        Integer.parseInt(
            request.getParameter("projectId") == null ? "0" : request.getParameter("projectId"));
    if (projectId == 0) return "没有选中项目";
    return null;
  }

  @Override
  protected WrapperOpt fetchWrapper(HttpServletRequest request) {
    int projectId =
        Integer.parseInt(
            request.getParameter("projectId") == null ? "0" : request.getParameter("projectId"));
    WrapperOpt wrapperOpt = new WrapperOpt();
    wrapperOpt.orderIsAsc = true;
    wrapperOpt.orderCondition = true;
    wrapperOpt.orderColumn = new ArrayList<>();
    wrapperOpt.orderColumn.add("sort");
    wrapperOpt.wheres = new HashMap<String, String>();
    wrapperOpt.wheres.put("own_id", projectId + "");
    return wrapperOpt;
  }
}
