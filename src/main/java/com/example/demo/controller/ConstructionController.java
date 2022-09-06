package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.Construction;
import com.example.demo.entity.Project;
import com.example.demo.service.IConstructionService;
import com.example.demo.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/construction")
public class ConstructionController extends BaseController<Construction> {
    @Autowired
    private IConstructionService constructionService;
    @Override
    protected IService getService(){
        return constructionService;
    }
}
