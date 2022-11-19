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
import com.example.demo.service.system.IRoleService;
import com.example.demo.service.system.ISettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController<Role> {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private ISettingService settingService;

    @Override
    protected IMyService fetchService() {
        return roleService;
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

    private void filterAuthor(JSONObject line, JSONObject author2, String[] row, String cmd) {
        String key = "";

        if (cmd.equals("")) {
            if (row[0].equals("all")) {
                key = line.getString("path");
            } else {
                key = row[0];
                if (!line.getString("path").equals(key)) {
                    return;
                }
            }
        } else {
            key = row[0];
        }

        JSONObject tmp__ = null;
        if (author2 != null) {
            tmp__ = author2.getJSONObject(key);

        }
        if (tmp__ == null) {
            tmp__ = new JSONObject();
            author2.put(key, tmp__);
        }
        if (!row[1].equals("-1")) {
            if (row[1].equals("0") || row[1].equals("1")) {
                tmp__.put("read", row[1]);
            } else {
                tmp__.put("read", "0");
            }

        } else if (!row[2].equals("-1")) {
            if (row[2].equals("0") || row[2].equals("1")) {
                tmp__.put("write", row[2]);
            } else {
                tmp__.put("write", "0");
            }
        }
    }

    @NeedLogin
    @PostMapping("/pushauthor")
    public ResData pushAuthor(@RequestBody String[] row, HttpServletRequest request) {
        // check
        if (row.length < 3) {
            ResData resData = new ResData();
            resData.setCode("200");
            resData.setErr("参数错误");
            resData.setMessage("");
            resData.setData(null);
            return resData;
        }

        QueryWrapper wrapper = new QueryWrapper();
        Map wheres = new HashMap<String, String>();
        wheres.put("item", "menu");
        wrapper.allEq(wheres);
        // Page<SubPackage> page2 = new Page(pageIndex, 5000);
        Setting setting = settingService.getOne(wrapper);
        //
        String roleId = request.getParameter("roleId") == null ? "" : request.getParameter("roleId");


        QueryWrapper wrapper2 = new QueryWrapper();
        Map wheres2 = new HashMap<String, String>();
        wheres2.put("role_id", roleId);
        wrapper2.allEq(wheres2);
        Role role = roleService.getOne(wrapper2);

        JSONObject author = JSON.parseObject(role.getAuthor());
        if (author == null) {
            author = new JSONObject();
        }
        JSONArray tmp = JSON.parseArray(setting.getContent());
        if (row[0].equals("all")) filterAuthor(null, author, row, "all");
        Setting.eachList(
                tmp,
                author,
                (JSONArray parent, JSONObject line, JSONObject author2, int index) -> {
                    filterAuthor(line, author2, row, "");
                    return -2;
                });
        JSONObject tmp2 = new JSONObject();
        tmp2.put("write", "0");
        tmp2.put("read", "1");
        author.put("/system/index", tmp2);
        System.out.println(author);
        role.setAuthor(JSON.toJSONString(author));
        roleService.updateById(role);

        ResData resData = new ResData();
        resData.setCode("200");
        // resData.setErr("参数错误");
        resData.setMessage("");
        resData.setData(null);
        return resData;
    }
}
