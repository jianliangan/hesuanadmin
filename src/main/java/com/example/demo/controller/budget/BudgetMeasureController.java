package com.example.demo.controller.budget;

import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.Project;
import com.example.demo.service.IMyService;
import com.example.demo.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/budget/measure")
public class BudgetMeasureController extends BaseController<Project> {

  @Autowired private IProjectService projectService;

  @Override
  protected IMyService getService() {
    return projectService;
  }

  @Override
  protected String commonPrePushCheck(HttpServletRequest request) {
    return null;
  }

  @Override
  protected String commonPreFetchCheck(HttpServletRequest request) {
    return null;
  }

  @Override
  protected WrapperOpt getWrapper(HttpServletRequest request) {
    int ownId =
        Integer.parseInt(
            request.getParameter("ownId") == null ? "-1" : request.getParameter("ownId"));
    WrapperOpt wrapperOpt = new WrapperOpt();
    wrapperOpt.orderIsAsc = true;
    wrapperOpt.orderCondition = true;
    wrapperOpt.orderColumn = new ArrayList<>();
    wrapperOpt.orderColumn.add("sort");
    wrapperOpt.wheres = new HashMap<String, String>();
    if (ownId != -1) wrapperOpt.wheres.put("own_id", ownId + "");
    return wrapperOpt;
  }
}
