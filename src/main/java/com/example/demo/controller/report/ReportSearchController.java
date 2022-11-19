package com.example.demo.controller.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.BaseController;
import com.example.demo.controller.common.NeedLogin;
import com.example.demo.document.Inventory;
import com.example.demo.service.IMyService;
import com.example.demo.service.common.PageData;
import com.example.demo.service.inventory.IInventoryService;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report/search")
public class ReportSearchController extends BaseController<Inventory> {
    @Autowired
    IInventoryService inventoryService;

    @Override
    protected IMyService fetchService() {
        return null;
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
        return null;
    }

    @NeedLogin
    @GetMapping("/fetch")
    public ResData getGeneral(HttpServletRequest request) {
        String err = null;
        PageData pageData = new PageData();
//?subject=11&code=22&category=33&distinction=333
        String subject = request.getParameter("subject");
        String code = request.getParameter("code");
        String category = request.getParameter("category");
        String distinction = request.getParameter("distinction");
        Map<String, String> qparams = new HashMap<>();
        if (subject != null && subject.length() > 0) {
            qparams.put("subject", subject);
        }
        if (code != null && code.length() > 0) {
            qparams.put("code", code);
        }
        if (category != null && category.length() > 0) {
            qparams.put("category", category);
        }
        if (distinction != null && distinction.length() > 0) {
            qparams.put("distinction", distinction);
        }
        err = commonPreFetchCheck(request);

        if (err == null) {

            int pageIndex =
                    Integer.parseInt(
                            request.getParameter("page") == null ? "0" : request.getParameter("page"));

            pageIndex = pageIndex == 0 ? 1 : pageIndex;

            QueryResponse queryResponse = inventoryService.getList(qparams);
            Long total = queryResponse.getResults().getNumFound();
            List<Inventory> inventoryList = queryResponse.getBeans(Inventory.class);
            pageData.setItemTotal(total);
            pageData.setPageSize(10);
            pageData.setList(inventoryList);
        }

        ResData resData = new ResData();
        resData.setCode("200");
        if (err != null) {
            resData.setErr(err);
        }
        /////
        pageData.setExtend(getDictInfosDivison());
/////
        resData.setData(pageData);
        resData.setMessage("");
        return resData;

    }
}
