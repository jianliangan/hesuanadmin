package com.example.demo.controller.dict;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.BaseController;
import com.example.demo.controller.common.NeedLogin;
import com.example.demo.controller.common.SessionUser;
import com.example.demo.entity.dict.SubPackage;
import com.example.demo.entity.system.Users;
import com.example.demo.service.IMyService;
import com.example.demo.service.common.PageData;
import com.example.demo.service.dict.ISubPackageService;
import com.example.demo.service.system.IUsersService;
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
@RequestMapping("/dict/subpackage")
public class SubPackageController extends BaseController<SubPackage> {

    @Autowired
    private ISubPackageService subPackageService;
    @Autowired
    private IUsersService usersService;

    @Override
    protected IMyService fetchService() {
        return subPackageService;
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

        String subpackageName = request.getParameter("subPackageName") == null ? "" : request.getParameter("subPackageName");
        String phone = request.getParameter("phone") == null ? "" : request.getParameter("phone");


        QueryWrapper wrapper = new QueryWrapper();
        List orderColumn = new ArrayList();
        orderColumn.add("sort");
        wrapper.orderBy(true, true, orderColumn);

        Map wheres = new HashMap<String, String>();
        wheres.put("own_id", ownId);
        if (ownId != "") wrapper.allEq(wheres);
        if (subpackageName != "")
            wrapper.likeRight("sub_package_name", subpackageName);
        if (phone != "")
            wrapper.likeRight("phone", phone);
        return wrapper;
    }

    public void filterUpdateRow(String pre, String cmd, SubPackage instance, HttpServletRequest request) {
        if (pre.equals("pre")) {
            if (cmd.equals("add")) {
                SessionUser sessionUser = (SessionUser) request.getAttribute("sessionUser");
                instance.setManager(sessionUser.getUserId());
//                instance.setCreateTime(new Date(System.currentTimeMillis()));
            } else if (cmd.equals("edit")) {
                SessionUser sessionUser = (SessionUser) request.getAttribute("sessionUser");
                instance.setManager(sessionUser.getUserId());
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
        List<SubPackage> list = (List<SubPackage>) pageData.getList();
        List mids = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            mids.add(list.get(i).getManager());
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
            Users tmp = (Users) dictMap.get(list.get(i).getManager());
            if (tmp != null) {
                list.get(i).setManagerName(tmp.getUsersName());
            }

        }
//        List statusl = new ArrayList();
//        statusl.add("正常");
//        statusl.add("停用");
//        JSONObject tmp = new JSONObject();
//        tmp.put("statusl", statusl);
//        pageData.setExtend(tmp);
        return resData;
    }
}
