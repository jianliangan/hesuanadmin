package com.example.demo.controller;

import com.example.demo.entity.Project;
import com.example.demo.mapper.ProjectMapper;
import com.example.demo.vo.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Resource
    ProjectMapper projectMapper;

    @GetMapping
    public List<Project> getProject() {
        return projectMapper.findAll();
    }

    @PostMapping
    public String addProject(@RequestBody Project user) {
        projectMapper.save(user);
        return "success";
    }

    @PutMapping
    public String updateProject(@RequestBody Project user) {
        projectMapper.updateById(user);
        return "success";
    }

    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable("id") Long id) {
        projectMapper.deleteById(id);
        return "success";
    }

    @GetMapping("/{id}")
    public Project findById(@PathVariable("id") Long id) {
        return projectMapper.findById(id);
    }

    @GetMapping("/page")
    public Page<Project> findByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        Integer offset = (pageNum - 1) * pageSize;
        List<Project> userData = projectMapper.findByPage(offset, pageSize);
        Page<Project> page = new Page<>();
        page.setData(userData);

        Integer total = projectMapper.countProject();
        page.setTotal(total);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        return page;
    }

}
