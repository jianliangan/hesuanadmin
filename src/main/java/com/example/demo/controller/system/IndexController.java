package com.example.demo.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.BaseController;
import com.example.demo.controller.common.NeedLogin;
import com.example.demo.controller.common.SessionUser;
import com.example.demo.entity.system.Role;
import com.example.demo.entity.system.Setting;
import com.example.demo.service.IMyService;
import com.example.demo.service.system.IRoleService;
import com.example.demo.service.system.ISettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/system/index")
public class IndexController extends BaseController {
    @Autowired
    private ISettingService settingService;
    @Autowired
    private IRoleService roleService;

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
        return null;
    }

    @Override
    protected IMyService fetchService() {
        return null;
    }

    @RequestMapping(
            value = "/ver",
            method = {RequestMethod.GET, RequestMethod.POST})
    public ResData getVer() {
        ResData resData = new ResData();
        resData.setCode("200");
        resData.setData("1.6.6");
        resData.setMessage("");
        return resData;
    }

    @NeedLogin
    @RequestMapping(
            value = "/getmenu",
            method = {RequestMethod.GET, RequestMethod.POST})
    public ResData getMenu(HttpServletRequest request) {
        ResData resData = new ResData();
        SessionUser sessionUser = (SessionUser) request.getAttribute("sessionUser");


        QueryWrapper wrapper = new QueryWrapper();
        Map wheres = new HashMap<String, String>();
        wheres.put("item", "menu");
        wrapper.allEq(wheres);


        Setting setting = settingService.getOne(wrapper);


        QueryWrapper wrapper2 = new QueryWrapper();
        Map wheres2 = new HashMap<String, String>();
        wheres2.put("role_id", sessionUser.getRoleId());
        wrapper2.allEq(wheres2);
        Role role = roleService.getOne(wrapper2);

        JSONArray menuList = JSON.parseArray(setting.getContent());
        JSONObject authorMap = JSON.parseObject(role.getAuthor());

        Setting.eachList(
                menuList,
                authorMap,
                (JSONArray parent, JSONObject line, JSONObject allMap, int index) -> {
                    if (line.get("path").equals("/dict/supplier")) {
                        System.out.println("d");
                    }
                    if (line.get("component") == null) {
                        return -2;
                    }
                    JSONObject tmp = (JSONObject) allMap.get(line.get("path"));
                    if (tmp == null || !tmp.get("read").equals("1")) {
                        parent.remove(index);
                        return index - 1;
                    }
                    return -2;
                });

        JSONObject group = new JSONObject();
        group.put("menu", menuList);

        resData.setCode("200");
        resData.setData(group);
        resData.setMessage("");
        return resData;
    }
}
