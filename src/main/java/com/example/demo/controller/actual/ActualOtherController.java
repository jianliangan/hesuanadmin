package com.example.demo.controller.actual;

import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.actual.ActualOther;
import com.example.demo.service.IMyService;
import com.example.demo.service.IProjectService;
import com.example.demo.service.actual.IActualOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/actual/other")
public class ActualOtherController extends BaseController<ActualOther> {

    @Autowired
    private IActualOtherService actualOtherService;
    @Autowired
    private IProjectService projectService;

    @Override
    protected IMyService fetchService() {
        return actualOtherService;
    }

    @Override
    protected String commonPrePushCheck(HttpServletRequest request) {
        // String projectId = request.getParameter("projectId");
        return null;
    }

    @Override
    protected String commonPreFetchCheck(HttpServletRequest request) {
        return null;
    }

}
