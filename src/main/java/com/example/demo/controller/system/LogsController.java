package com.example.demo.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.system.Logs;
import com.example.demo.service.IMyService;
import com.example.demo.service.system.ILogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system/logs")
public class LogsController extends BaseController<Logs> {

    @Autowired
    private ILogsService logsService;

    @Override
    protected IMyService fetchService() {
        return logsService;
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
    protected QueryWrapper fetchWrapper(HttpServletRequest request) {
        QueryWrapper wrapper = new QueryWrapper();

        List orderColumn = new ArrayList<>();
        orderColumn.add("datetime");
        wrapper.orderBy(true, false, orderColumn);
        return wrapper;
    }
}
