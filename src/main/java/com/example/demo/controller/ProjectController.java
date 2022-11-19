package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.BaseController;
import com.example.demo.controller.common.NeedLogin;
import com.example.demo.entity.Project;
import com.example.demo.entity.dict.Dict;
import com.example.demo.service.IMyService;
import com.example.demo.service.IProjectService;
import com.example.demo.service.common.PageData;
import com.example.demo.service.dict.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home/project")
public class ProjectController extends BaseController<Project> {

    @Autowired
    private IProjectService projectService;
    @Autowired
    private IDictService dictService;

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
        return null;
    }

    @NeedLogin
    @GetMapping("/fetchwith")
    public ResData fetch(HttpServletRequest request) {
        ResData resData = getGeneral(request);

        QueryWrapper wrapper = new QueryWrapper();
        Map wheres = new HashMap<String, String>();
        wheres.put("type_name", "billmode");
        wrapper.allEq(wheres);
        List<Dict> dictList = dictService.list(wrapper);
        PageData pageData = (PageData) resData.getData();
        JSONObject tmp = new JSONObject();
        tmp.put("billmode", dictList);
        pageData.setExtend(tmp);
        return resData;
    }
}
