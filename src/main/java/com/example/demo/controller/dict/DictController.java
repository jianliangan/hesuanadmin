package com.example.demo.controller.dict;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.BaseController;
import com.example.demo.controller.common.NeedLogin;
import com.example.demo.entity.dict.Dict;
import com.example.demo.service.IMyService;
import com.example.demo.service.dict.IDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/dict/dict")
public class DictController extends BaseController<Dict> {
    @Autowired
    private IDictService dictService;

    @Override
    protected IMyService fetchService() {
        return dictService;
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
        List orderColumn = new ArrayList();
        orderColumn.add("sort");
        wrapper.orderBy(true, true, orderColumn);

        Map wheres = new HashMap<String, String>();

        if (request.getRequestURI().indexOf("/dict/dict/category") != -1)
            wheres.put("type_name", "category");
        else if (request.getRequestURI().indexOf("/dict/dict/subject") != -1)
            wheres.put("type_name", "subject");
        else if (request.getRequestURI().indexOf("/dict/dict/billmode") != -1)
            wheres.put("type_name", "billmode");
        else if (request.getRequestURI().indexOf("/dict/dict/suppliertype") != -1)
            wheres.put("type_name", "suppliertype");

        wrapper.allEq(wheres);

        return wrapper;

    }

    @NeedLogin
    @GetMapping("/category/fetch")
    public ResData categoryFetch(HttpServletRequest request) {
        return getGeneral(request);
    }

    @NeedLogin
    @PostMapping("/category/push")
    public ResData categoryPush(@RequestBody List<Dict> rows, HttpServletRequest request) {
        for (int i = 0; i < rows.size(); i++) {
            Dict dict = (Dict) rows.get(i);
            dict.setTypeName("category");
        }
        return addGeneral(rows, request);
    }

    @NeedLogin
    @GetMapping("/subject/fetch")
    public ResData subjectFetch(HttpServletRequest request) {
        return getGeneral(request);
    }

    @NeedLogin
    @PostMapping("/subject/push")
    public ResData subjectPush(@RequestBody List<Dict> rows, HttpServletRequest request) {
        for (int i = 0; i < rows.size(); i++) {
            Dict dict = (Dict) rows.get(i);
            dict.setTypeName("subject");
        }
        return addGeneral(rows, request);
    }

    @NeedLogin
    @GetMapping("/billmode/fetch")
    public ResData billmodeFetch(HttpServletRequest request) {
        return getGeneral(request);
    }

    @NeedLogin
    @PostMapping("/billmode/push")
    public ResData billmodePush(@RequestBody List<Dict> rows, HttpServletRequest request) {
        for (int i = 0; i < rows.size(); i++) {
            Dict dict = (Dict) rows.get(i);
            dict.setTypeName("billmode");
        }
        return addGeneral(rows, request);
    }

    @NeedLogin
    @GetMapping("/suppliertype/fetch")
    public ResData suppliertypeFetch(HttpServletRequest request) {
        return getGeneral(request);
    }

    @NeedLogin
    @PostMapping("/suppliertype/push")
    public ResData suppliertypePush(@RequestBody List<Dict> rows, HttpServletRequest request) {
        for (int i = 0; i < rows.size(); i++) {
            Dict dict = (Dict) rows.get(i);
            dict.setTypeName("suppliertype");
        }
        return addGeneral(rows, request);
    }
}
