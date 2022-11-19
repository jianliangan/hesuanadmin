package com.example.demo.controller.budget;

import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.budget.BudgetOther;
import com.example.demo.service.IMyService;
import com.example.demo.service.IProjectService;
import com.example.demo.service.budget.IBudgetOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/budget/other")
public class BudgetOtherController extends BaseController<BudgetOther> {

    @Autowired
    private IBudgetOtherService budgetOtherService;
    @Autowired
    private IProjectService projectService;

    @Override
    protected IMyService fetchService() {
        return budgetOtherService;
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
