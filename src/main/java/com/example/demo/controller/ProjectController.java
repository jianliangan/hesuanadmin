package com.example.demo.controller;

import com.example.demo.entity.Project;

import com.example.demo.service.IProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @GetMapping("/fetch")
    public List<Project> getProject() {
        return projectService.list();
    }

    // @PostMapping
    // public String addProject(@RequestBody Project user) {
    // projectMapper.save(user);
    // return "success";
    // }

    // @PutMapping
    // public String updateProject(@RequestBody Project user) {
    // projectMapper.updateById(user);
    // return "success";
    // }

    // @DeleteMapping("/{id}")
    // public String deleteProject(@PathVariable("id") Long id) {
    // projectMapper.deleteById(id);
    // return "success";
    // }

    // @GetMapping("/{id}")
    // public Project findById(@PathVariable("id") Long id) {
    // return projectMapper.findById(id);
    // }

    // @GetMapping("/page")
    // public Page<Project> findByPage(@RequestParam(defaultValue = "1") Integer
    // pageNum,
    // @RequestParam(defaultValue = "10") Integer pageSize) {
    // // Integer offset = (pageNum - 1) * pageSize;
    // // List<Project> userData = projectMapper.findByPage(offset, pageSize);
    // // Page<Project> page = new Page<>();
    // // page.setData(userData);

    // // Integer total = projectMapper.countProject();
    // // page.setTotal(total);
    // // page.setPageNum(pageNum);
    // // page.setPageSize(pageSize);
    // // return page;
    // }

}
