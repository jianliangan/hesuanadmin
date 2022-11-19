package com.example.demo.controller.dict;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.BaseController;
import com.example.demo.controller.common.NeedLogin;
import com.example.demo.controller.common.SessionUser;
import com.example.demo.entity.dict.SupplyUnit;
import com.example.demo.entity.system.Users;
import com.example.demo.service.IMyService;
import com.example.demo.service.common.PageData;
import com.example.demo.service.dict.IDictService;
import com.example.demo.service.dict.ISupplyUnitService;
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
@RequestMapping("/dict/supplyunit")
public class SupplyUnitController extends BaseController<SupplyUnit> {

    @Autowired
    private ISupplyUnitService supplyUnitService;
    @Autowired
    private IDictService dictService;
    @Autowired
    private IUsersService usersService;

    @Override
    protected IMyService fetchService() {
        return supplyUnitService;
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

        String supplyUnitName = request.getParameter("supplyUnitName") == null ? "" : request.getParameter("supplyUnitName");
        String phone = request.getParameter("phone") == null ? "" : request.getParameter("phone");


        QueryWrapper wrapper = new QueryWrapper();
        List orderColumn = new ArrayList();
        orderColumn.add("sort");
        wrapper.orderBy(true, true, orderColumn);

        Map wheres = new HashMap<String, String>();
        wheres.put("own_id", ownId);
        if (ownId != "") wrapper.allEq(wheres);
        if (supplyUnitName != "")
            wrapper.likeRight("supply_unit_name", supplyUnitName);
        if (phone != "")
            wrapper.likeRight("phone", phone);
        return wrapper;
    }

    @NeedLogin
    @GetMapping("/fetchwith")
    public ResData fetch(HttpServletRequest request) {
        ResData resData = getGeneral(request);
        PageData pageData = (PageData) resData.getData();
        ////
        List<SupplyUnit> list = (List<SupplyUnit>) pageData.getList();
        List mids = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            mids.add(list.get(i).getManager());
        }
        QueryWrapper wrapperNew = new QueryWrapper();
        if (mids.size() > 0)
            wrapperNew.in("users_id", mids.toArray());
        List<Users> dictListNew = usersService.list(wrapperNew);
        Map dictMap = new HashMap();
        for (int i = 0; i < dictListNew.size(); i++) {
            dictMap.put(dictListNew.get(i).getUsersId(), dictListNew.get(i));
        }
        for (int i = 0; i < list.size(); i++) {
            Users tmpNew = (Users) dictMap.get(list.get(i).getManager());
            if (tmpNew != null) {
                list.get(i).setManagerName(tmpNew.getUsersName());
            }

        }
        JSONObject tmp = getDictInfosSuppliertype();
        pageData.setExtend(tmp);
        return resData;
    }

    public void filterUpdateRow(String pre, String cmd, SupplyUnit instance, HttpServletRequest request) {
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
}
