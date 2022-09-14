package com.example.demo.controller.budget;

import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.budget.BudgetDivision;
import com.example.demo.service.IMyService;
import com.example.demo.service.budget.IBudgetDivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/budget/division")
public class BudgetDivisionController extends BaseController<BudgetDivision> {

  @Autowired private IBudgetDivisionService budgetDivisionService;

  @Override
  protected IMyService getService() {
    return budgetDivisionService;
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
    int projectId =
        Integer.parseInt(
            request.getParameter("projectId") == null ? "-1" : request.getParameter("projectId"));
    WrapperOpt wrapperOpt = new WrapperOpt();
    wrapperOpt.orderIsAsc = true;
    wrapperOpt.orderCondition = true;
    wrapperOpt.orderColumn = new ArrayList<>();
    wrapperOpt.orderColumn.add("sort");
    wrapperOpt.wheres = new HashMap<String, String>();
    if (projectId != -1) wrapperOpt.wheres.put("project_id", projectId + "");
    return wrapperOpt;
  }
}
