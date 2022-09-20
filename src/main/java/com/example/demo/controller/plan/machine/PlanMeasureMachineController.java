package com.example.demo.controller.plan.machine;

import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.plan.machine.PlanMeasureMachine;
import com.example.demo.service.IMyService;
import com.example.demo.service.plan.machine.IPlanMeasureMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/plan/measure/machine")
public class PlanMeasureMachineController extends BaseController<PlanMeasureMachine> {

  @Autowired private IPlanMeasureMachineService planMeasureMachineService;

  @Override
  protected IMyService fetchService() {
    return planMeasureMachineService;
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
