package com.example.demo.controller.budget.machine;

import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.budget.machine.BudgetMeasureMachine;
import com.example.demo.service.IMyService;
import com.example.demo.service.budget.machine.IBudgetMeasureMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/budget/measure/machine")
public class BudgetMeasureMachineController extends BaseController<BudgetMeasureMachine> {

  @Autowired private IBudgetMeasureMachineService budgetMeasureMachineService;

  @Override
  protected IMyService fetchService() {
    return budgetMeasureMachineService;
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
  protected WrapperOpt fetchWrapper(HttpServletRequest request) {
    String ownId = request.getParameter("ownId") == null ? "" : request.getParameter("ownId");

    WrapperOpt wrapperOpt = new WrapperOpt();
    wrapperOpt.orderIsAsc = true;
    wrapperOpt.orderCondition = true;
    wrapperOpt.orderColumn = new ArrayList<>();
    wrapperOpt.orderColumn.add("sort");
    wrapperOpt.wheres = new HashMap<String, String>();
    wrapperOpt.wheres.put("own_id", ownId);
    return wrapperOpt;
  }
}