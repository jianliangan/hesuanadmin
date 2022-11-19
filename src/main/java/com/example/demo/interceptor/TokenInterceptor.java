package com.example.demo.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.NeedLogin;
import com.example.demo.controller.common.SessionUser;
import com.example.demo.controller.common.Tools;
import com.example.demo.entity.system.Logs;
import com.example.demo.entity.system.Role;
import com.example.demo.entity.system.Users;
import com.example.demo.service.system.ILogsService;
import com.example.demo.service.system.IRoleService;
import com.example.demo.service.system.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private ILogsService logsService;
    @Autowired
    private IUsersService usersService;


    private static final Map<String, String> pathMap = new HashMap<String, String>();

    static {
        pathMap.put("/report/search/fetch", "清单搜索");
        pathMap.put("/home/project/fetchwith", "项目列表");
        pathMap.put("/home/project/push", "项目更改");
        pathMap.put("/home/project/push", "项目更改");

        pathMap.put("/budget/division/tree2", "预算分部分项列表");
        pathMap.put("/budget/division/push", "预算分部分项更改");
        pathMap.put("/budget/division/machine/push", "预算分项工料机更改");
        pathMap.put("/budget/measure/tree2", "预算措施分项列表");
        pathMap.put("/budget/measure/push", "预算措施分项更改");
        pathMap.put("/budget/measure/machine/push", "预算措施分项工料机更改");
        pathMap.put("/budget/other/fetch", "预算其他列表");
        pathMap.put("/budget/other/push", "预算其他更改");

        pathMap.put("/plan/division/tree2", "计划分部分项列表");
        pathMap.put("/plan/division/push", "计划分部分项更改");
        pathMap.put("/plan/division/machine/push", "计划分项工料机更改");
        pathMap.put("/plan/measure/tree2", "计划措施分项列表");
        pathMap.put("/plan/measure/push", "计划措施分项更改");
        pathMap.put("/plan/measure/machine/push", "计划措施分项工料机更改");
        pathMap.put("/plan/other/fetch", "计划其他列表");
        pathMap.put("/plan/other/push", "计划其他更改");

        pathMap.put("/actual/division/tree2", "实际分部分项列表");
        pathMap.put("/actual/division/push", "实际分部分项更改");
        pathMap.put("/actual/division/machine/push", "实际分项工料机更改");
        pathMap.put("/actual/measure/tree2", "实际措施分项列表");
        pathMap.put("/actual/measure/push", "实际措施分项更改");
        pathMap.put("/actual/measure/machine/push", "实际措施分项工料机更改");
        pathMap.put("/actual/other/fetch", "实际其他列表");
        pathMap.put("/actual/other/push", "实际其他更改");

        pathMap.put("/report/project/tree2", "单位工程查询");
        pathMap.put("/report/subpackage/tree2", "分包商查询");

        pathMap.put("/dict/subpackage/fetchwith", "分包商列表");
        pathMap.put("/dict/subpackage/push", "分包商更改");
        pathMap.put("/dict/supplyunit/fetchwith", "供应商列表");
        pathMap.put("/dict/supplyunit/push", "供应商更改");
        pathMap.put("/dict/materials/fetchwith", "材料库列表");
        pathMap.put("/dict/materials/push", "材料库更改");
        pathMap.put("/dict/dict/category/fetch", "分类列表");
        pathMap.put("/dict/dict/category/push", "分类更改");
        pathMap.put("/dict/dict/subject/fetch", "科目列表");
        pathMap.put("/dict/dict/subject/push", "科目更改");
        pathMap.put("/dict/dict/billmode/fetch", "计费方式列表");
        pathMap.put("/dict/dict/billmode/push", "计费方式更改");
        pathMap.put("/dict/dict/suppliertype/fetch", "供应商类型列表");
        pathMap.put("/dict/dict/suppliertype/push", "供应商类型更改");
        pathMap.put("/system/users/fetch", "用户列表");
        pathMap.put("/system/users/push", "用户更改");
        pathMap.put("/system/role/fetch", "角色列表");
        pathMap.put("/system/role/push", "角色更改");
        pathMap.put("/system/author/menu", "授权列表");
        pathMap.put("/system/role/pushauthor", "授权更改");
        pathMap.put("/budget/import", "导入");
    }


    private void resData(String err, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "200");
        jsonObject.put("err", err);
        jsonObject.put("message", "");
        jsonObject.put("data", null);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.print(jsonObject);
        writer.flush();
        writer.close();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String type = request.getMethod();
        if (type.equals("OPTIONS")) {
            return true;
        }


        Map<String, Object> map = new HashMap<>();

        String token = request.getHeader("author");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 检查是否有NeedLogin注释
        if (method.isAnnotationPresent(NeedLogin.class)) {

            // 捕获刚刚JWT中抛出的异常,并封装对应的返回信息
            SessionUser sessionUser = new SessionUser();
            DecodedJWT decodedJWT = Tools.verifyToken(token);

            // 封装返回值
            if (decodedJWT == null) {
                resData("权限校验失败", response);
                return false;
            } else {
                String userId = decodedJWT.getClaim("userId").asString();
                //log.info("dddddddddd{},{},{}", decodedJWT.getClaim("userId").asString());
                String uri = request.getRequestURI();
                String module = "";
                int indexOf1 = uri.indexOf('/', 1);
                int indexOf2 = uri.indexOf('/', indexOf1 + 1);
                if (indexOf2 == -1) {
                    module = uri;
                } else {
                    module = uri.substring(0, indexOf2);
                }

                String roleId = decodedJWT.getClaim("roleId").asString();

                sessionUser.setUserId(userId);
                sessionUser.setRoleId(roleId);

                // log.info("sssssssssssssssssss{}", request.getRequestURI());

                QueryWrapper wrapper = new QueryWrapper();
                Map wheres = new HashMap<String, String>();
                wheres.put("users_id", userId);
                wrapper.allEq(wheres);
                // Page<SubPackage> page2 = new Page(pageIndex, 5000);
                Users users = usersService.getOne(wrapper);

                Logs logs = new Logs();
                logs.setUserName(users.getUsersName());
                logs.setIp(Tools.getInIp(request));
                logs.setBrowser(Tools.getBrowser(request));
                logs.setAction(request.getRequestURI());

                String name = pathMap.get(request.getRequestURI());
                if (name != null) {
                    String body = new String();
//                    try (InputStream is = request.getInputStream()) {
//                        body = IOUtils.toString(is, StandardCharsets.UTF_8);
//                        logs.setBody(body);
//                    } catch (IOException ex) {
//                        log.error("read http request failed.", ex);
//                    }
                    logs.setActionInfos(name);

                }


                logs.setParams(request.getQueryString());
                request.setAttribute("sessionUser", sessionUser);
                if (roleId.equals("1")) { // 超级管理员
                    logs.setStatus("正常");
                    if (name != null) {
                        logsService.save(logs);
                    }

                    return true;
                }

                QueryWrapper wrapper2 = new QueryWrapper();
                Map wheres2 = new HashMap<String, String>();
                wheres2.put("role_id", roleId);
                wrapper2.allEq(wheres2);
                Role role = roleService.getOne(wrapper2);
                if (role == null) {
                    resData("权限校验失败", response);
                    logs.setStatus("权限校验失败");
                    if (name != null) {
                        logsService.save(logs);
                    }
                    return false;
                }
                JSONObject author = JSON.parseObject(role.getAuthor());
                JSONObject aut = (JSONObject) author.get(module);
                if (aut == null) {
                    resData("权限校验失败", response);
                    logs.setStatus("权限校验失败");
                    if (name != null) {
                        logsService.save(logs);
                    }
                    return false;
                }

                if (type.equals("GET")) {
                    if (aut.get("read") != null && aut.get("read").equals("1")) {
                        // 可读
                        logs.setStatus("可读");
                        if (name != null) {
                            logsService.save(logs);
                        }
                        return true;
                    } else {
                        resData("权限校验失败", response);
                        logs.setStatus("权限校验失败");
                        if (name != null) {
                            logsService.save(logs);
                        }
                        return false;
                    }
                } else if (type.equals("POST")) {
                    if (aut.get("write") != null && aut.get("write").equals("1")) {
                        // 可写
                        logs.setStatus("可写");
                        if (name != null) {
                            logsService.save(logs);
                        }
                        return true;
                    } else {
                        resData("权限校验失败", response);
                        logs.setStatus("权限校验失败");
                        if (name != null) {
                            logsService.save(logs);
                        }
                        return false;
                    }
                }
                logs.setStatus("正常");
                if (name != null) {
                    logsService.save(logs);
                }
            }
        }
        return true;
    }
}
