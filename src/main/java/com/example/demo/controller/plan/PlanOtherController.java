package com.example.demo.controller.plan;

import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.plan.PlanOther;
import com.example.demo.service.IMyService;
import com.example.demo.service.IProjectService;
import com.example.demo.service.plan.IPlanOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/plan/other")
public class PlanOtherController extends BaseController<PlanOther> {

    @Autowired
    private IPlanOtherService planOtherService;
    @Autowired
    private IProjectService projectService;

    @Override
    protected IMyService fetchService() {
        return planOtherService;
    }

    @Override
    protected String commonPrePushCheck(HttpServletRequest request) {
        return null;
    }

    @Override
    protected String commonPreFetchCheck(HttpServletRequest request) {
        return null;
    }


}
