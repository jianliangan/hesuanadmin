package com.example.demo.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.BaseController;
import com.example.demo.controller.common.NeedLogin;
import com.example.demo.entity.system.Role;
import com.example.demo.entity.system.Setting;
import com.example.demo.service.IMyService;
import com.example.demo.service.common.PageData;
import com.example.demo.service.system.IRoleService;
import com.example.demo.service.system.ISettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/author")
public class AuthorController extends BaseController<Setting> {

    @Autowired
    private ISettingService settingService;

    @Autowired
    private IRoleService roleService;

    @Override
    protected IMyService fetchService() {
        return settingService;
    }

    @NeedLogin
    @GetMapping("/menu")
    public ResData menu(HttpServletRequest request) {


        String roleId = request.getParameter("roleId") == null ? "" : request.getParameter("roleId");
        Map wheres = new HashMap<String, String>();
        wheres.put("item", "menu");
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.allEq(wheres);
        // Page<SubPackage> page2 = new Page(pageIndex, 5000);
        Setting setting = settingService.getOne(wrapper);

        Map wheres2 = new HashMap<String, String>();
        wheres2.put("role_id", roleId);
        QueryWrapper wrapper2 = new QueryWrapper();
        wrapper2.allEq(wheres2);
        Role role = roleService.getOne(wrapper2);

        JSONArray tmp = JSON.parseArray(setting.getContent());
        JSONObject tmp2 = JSON.parseObject(role.getAuthor());
        //    Setting.eachList(
        //        tmp,
        //        tmp2,
        //        (JSONObject line, JSONObject tmptmp) -> {
        //          line.put("author", tmptmp.get(line.get("path")));
        //        });

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("author", tmp2);

        PageData pageData = new PageData();
        pageData.setPageSize(10);
        pageData.setItemTotal(10);
        pageData.setList(tmp);
        pageData.setExtend(jsonObject);
        ResData resData = new ResData();
        resData.setCode("200");

        //    if (err != null) {
        //      resData.setErr(err);
        //    }
        resData.setMessage("");
        resData.setData(pageData);
        return resData;
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


        String ownId = request.getParameter("ownId") == null ? "" : request.getParameter("ownId");
        QueryWrapper wrapper = new QueryWrapper();
        List orderColumn = new ArrayList();
        orderColumn.add("sort");
        wrapper.orderBy(true, true, orderColumn);

        Map wheres = new HashMap<String, String>();
        wheres.put("own_id", ownId);
        if (ownId != "") wrapper.allEq(wheres);

        return wrapper;
    }
}
