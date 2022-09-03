package com.example.demo.controller;

import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.Project;
import com.example.demo.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/areas")
public class AreasController extends BaseController {

    @Autowired
    private IProjectService projectService;

    @GetMapping("/areas")
    public String getAreas(HttpServletRequest request) {
        return "areas/areas.json";
    }

}
