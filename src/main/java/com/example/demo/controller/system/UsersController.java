package com.example.demo.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.controller.common.BaseController;
import com.example.demo.controller.common.NeedLogin;
import com.example.demo.controller.common.Tools;
import com.example.demo.entity.system.Logs;
import com.example.demo.entity.system.Role;
import com.example.demo.entity.system.Users;
import com.example.demo.service.IMyService;
import com.example.demo.service.common.PageData;
import com.example.demo.service.system.ILogsService;
import com.example.demo.service.system.IRoleService;
import com.example.demo.service.system.IUsersService;
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
@RequestMapping("/system/users")
public class UsersController extends BaseController<Users> {

    @Autowired
    private IUsersService usersService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private ILogsService logsService;

    @Override
    protected IMyService fetchService() {
        return usersService;
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

    @NeedLogin
    @GetMapping("/fetch")
    public ResData getGeneral(HttpServletRequest request) {
        String err = null;
        PageData pageData = new PageData();
        if (fetchService() == null) {
            err = "服务器错误";
        }
        if (err == null) {
            err = commonPreFetchCheck(request);

            if (err == null) {

                int pageIndex =
                        Integer.parseInt(
                                request.getParameter("page") == null ? "0" : request.getParameter("page"));

                pageIndex = pageIndex == 0 ? 1 : pageIndex;
                // List<T> list = fetchService().list();
                // roles
                List<Role> listRole = roleService.list();
                Map<String, Role> mapRole = new HashMap<String, Role>();
                for (int i = 0; i < listRole.size(); i++) {
                    mapRole.put(listRole.get(i).getRoleId(), listRole.get(i));
                }

                // users
                Page<Users> page = new Page(pageIndex, pageSize);
                QueryWrapper wrapper = fetchWrapper(request);
                IPage userPage = fetchService().page(page, wrapper);
                System.out.println(",,,,,,,,,," + userPage);
                // userPage.getRecords().forEach(System.out::println);
                List<Users> list = userPage.getRecords();
                int itemOffset = (pageIndex - 1) * pageSize;
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSortR(itemOffset + i + 1);
                }

                long total = fetchService().count(wrapper);
                userPage.setTotal(total);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("roles", mapRole);
                pageData.setItemTotal(userPage.getTotal());
                pageData.setPageSize(userPage.getSize());
                pageData.setList(list);
                pageData.setExtend(jsonObject.toJavaObject(Object.class));
            }
        }
        ResData resData = new ResData();
        resData.setCode("200");
        if (err != null) {
            resData.setErr(err);
        }
        resData.setData(pageData);
        resData.setMessage("");
        return resData;
    }

    @RequestMapping(
            value = "/token",
            method = {RequestMethod.GET, RequestMethod.POST})
    public ResData postToken(@RequestBody JSONObject row, HttpServletRequest request) {
        String usersName = row.getString("userName");
        String passWord = row.getString("passWord");


        QueryWrapper wrapper = new QueryWrapper();
        Map wheres = new HashMap<String, String>();
        wheres.put("users_name", usersName);
        wrapper.allEq(wheres);
        // Page<SubPackage> page2 = new Page(pageIndex, 5000);
        Users users = usersService.getOne(wrapper);
        ResData resData = new ResData();
        if (users == null || !users.getPassWord().equals(passWord)) {
            resData.setCode("200");
            resData.setData(null);
            resData.setErr("用户名密码错误");
            resData.setMessage("");

            Logs logs = new Logs();
            logs.setUserName(usersName);
            logs.setIp(Tools.getInIp(request));
            logs.setBrowser(Tools.getBrowser(request));
            logs.setStatus("失败");
            logsService.save(logs);
            return resData;
        }

        QueryWrapper wrapper2 = new QueryWrapper();
        Map wheres2 = new HashMap<String, String>();
        wheres2.put("role_id", users.getRoleId());
        wrapper2.allEq(wheres2);
        Role role = roleService.getOne(wrapper2);

        int start = (int) (System.currentTimeMillis() / 1000);
        String token =
                Tools.createToken(users.getUsersId(), users.getUsersName(), users.getRoleId(), start);
        JSONObject tokenInfo = new JSONObject();

        JSONObject userInfo = new JSONObject();

        userInfo.put("userId", users.getUsersId());

        userInfo.put("userName", users.getUsersName());

        userInfo.put("dashboard", "0");

        tokenInfo.put("token", token);

        tokenInfo.put("userInfo", userInfo);

        tokenInfo.put("role", users.getRoleId());
        resData.setCode("200");
        resData.setData(tokenInfo);
        resData.setMessage("");


        Logs logs = new Logs();
        logs.setUserName(usersName);
        logs.setIp(Tools.getInIp(request));
        logs.setBrowser(Tools.getBrowser(request));
        logs.setStatus("登录成功");
        logsService.save(logs);


        return resData;
    }
}
