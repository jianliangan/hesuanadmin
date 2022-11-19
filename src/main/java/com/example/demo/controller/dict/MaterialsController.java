package com.example.demo.controller.dict;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.BaseController;
import com.example.demo.controller.common.NeedLogin;
import com.example.demo.controller.common.SessionUser;
import com.example.demo.entity.dict.Materials;
import com.example.demo.entity.system.Users;
import com.example.demo.service.IMyService;
import com.example.demo.service.common.PageData;
import com.example.demo.service.dict.IMaterialsService;
import com.example.demo.service.system.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dict/materials")
public class MaterialsController extends BaseController<Materials> {

    @Autowired
    private IMaterialsService materialsService;
    @Autowired
    private IUsersService usersService;

    @Override
    protected IMyService fetchService() {
        return materialsService;
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

        String code = request.getParameter("code") == null ? "" : request.getParameter("code");
        String materialsName = request.getParameter("materialsName") == null ? "" : request.getParameter("materialsName");
        QueryWrapper wrapper = new QueryWrapper();

        List orderColumn = new ArrayList<>();
        orderColumn.add("sort");
        wrapper.orderBy(true, true, orderColumn);

        wrapper.likeRight("code", code);
        wrapper.likeRight("materials_name", materialsName);

        return wrapper;
    }

    public void filterUpdateRow(String pre, String cmd, Materials materials, HttpServletRequest request) {
        if (pre.equals("pre")) {
            if (cmd.equals("add")) {
                SessionUser sessionUser = (SessionUser) request.getAttribute("sessionUser");
                materials.setCreateBy(sessionUser.getUserId());
                materials.setCreateTime(new Date(System.currentTimeMillis()));
            } else if (cmd.equals("edit")) {
                SessionUser sessionUser = (SessionUser) request.getAttribute("sessionUser");
                materials.setCreateBy(sessionUser.getUserId());
                materials.setCreateTime(new Date(System.currentTimeMillis()));
            } else if (cmd.equals("delete")) {

            }
        } else if (pre.equals("last")) {
            if (cmd.equals("add")) {

            } else if (cmd.equals("edit")) {

            } else if (cmd.equals("delete")) {

            }
        }
    }

    @NeedLogin
    @GetMapping("/fetchwith")
    public ResData fetch(HttpServletRequest request) {
        ResData resData = getGeneral(request);
        PageData pageData = (PageData) resData.getData();
        List<Materials> list = (List<Materials>) pageData.getList();
        List mids = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            mids.add(list.get(i).getCreateBy());
        }
        QueryWrapper wrapper = new QueryWrapper();
        if (mids.size() > 0)
            wrapper.in("users_id", mids.toArray());
        List<Users> dictList = usersService.list(wrapper);
        Map dictMap = new HashMap();
        for (int i = 0; i < dictList.size(); i++) {
            dictMap.put(dictList.get(i).getUsersId(), dictList.get(i));
        }
        for (int i = 0; i < list.size(); i++) {
            Users tmp = (Users) dictMap.get(list.get(i).getCreateBy());
            if (tmp != null) {
                list.get(i).setCreateByName(tmp.getUsersName());
            }

        }
        List statusl = new ArrayList();
        statusl.add("正常");
        statusl.add("停用");
        JSONObject tmp = getDictInfosDivison();
        tmp.put("statusl", statusl);
        pageData.setExtend(tmp);
        return resData;
    }
}
