package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.Project;

import com.example.demo.service.IProjectService;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController<Project> {

    @Autowired
    private IProjectService projectService;
    @Override
    protected IService getService(){
        return projectService;
    }
//    @GetMapping("/fetch")
//    public ResData getGeneral(HttpServletRequest request) {
//        int page = Integer.parseInt(request.getParameter("page") == null ? "0" : request.getParameter("page"));
//
//        List<Project> list = projectService.list();
//        PageData pageData = new PageData();
//        pageData.setPagenum(page);
//        pageData.setPagesize(pageSize);
//        pageData.setList(list);
//        ResData resData = new ResData();
//        resData.setCode("200");
//        resData.setData(pageData);
//        resData.setMessage("");
//        return resData;
//    }
//
//    @PostMapping("/push")
//    public String addGeneral(@RequestBody Project project) {
//
//        System.out.println( project.getProjectName());
//        if (project.getCmd().equals("edit")) {
//            projectService.updateById(project);
//        } else if (project.getCmd().equals("add")) {
//            projectService.save(project);
//        } else if (project.getCmd().equals("delete")) {
//            projectService.removeById(project);
//        }
//        return "success";
//    }

}
