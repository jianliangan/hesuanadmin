package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.controller.common.BaseController;
import com.example.demo.controller.common.storage.StorageService;
import com.example.demo.entity.Construction;
import com.example.demo.service.IMyService;
import com.example.demo.service.budget.IBudgetDivisionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/budget")
public class BudgetController extends BaseController<Construction> {
    @Autowired
    private ApplicationContext applicationContext;
    private final StorageService storageService;
    @Autowired
    private IBudgetDivisionService budgetDivisionService;


    @Autowired
    public BudgetController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    protected IMyService fetchService() {
        return null;
    }

    @Override
    protected QueryWrapper fetchWrapper(HttpServletRequest request) {
        return null;
    }

    @Override
    protected String commonPrePushCheck(HttpServletRequest request) {
        return null;
    }

    @Override
    protected String commonPreFetchCheck(HttpServletRequest request) {

        if (request.getParameter("ownId") == null || request.getParameter("ownId").length() == 0)
            return "没有选中项目";
        return null;
    }

    protected IService fetchService2() {
        return null;
    }

    @PostMapping("/import")
    public ResData getGeneral2(
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) throws Exception {
        // log.info("路徑是{}", System.getProperty("user.dir"));
        // 上传excel
        String err = null;
        err = commonPreFetchCheck(request);
        if (err == null) {
            String ownId = request.getParameter("ownId") == null ? "" : request.getParameter("ownId");
            String projectId =
                    request.getParameter("projectId") == null ? "" : request.getParameter("projectId");

            String filePath = storageService.store(file);
            ////////

            try {
                budgetDivisionService.ImportData(projectId, filePath);
            } catch (Exception e) {
                throw e;
            }
        }
        ResData resData = new ResData();
        resData.setCode("200");
        if (err != null) {
            resData.setErr(err);
        }
        resData.setData("");
        resData.setMessage("");
        return resData;
    }
}
