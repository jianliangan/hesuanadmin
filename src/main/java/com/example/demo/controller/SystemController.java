package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.Project;

import com.example.demo.service.IProjectService;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system")
public class SystemController extends BaseController {

    @RequestMapping(value = "/ver", method = { RequestMethod.GET, RequestMethod.POST })
    public ResData getVer() {
        ResData resData = new ResData();
        resData.setCode("200");
        resData.setData("1.6.6");
        resData.setMessage("");
        return resData;
    }

    @RequestMapping(value = "/token", method = { RequestMethod.GET, RequestMethod.POST })
    public ResData postToken() {
        ResData resData = new ResData();
        TokenInfo tokenInfo = new TokenInfo();
        /// tmp :=
        /// `{"token":"SCUI.Administrator.Auth",
        // "userInfo":{"userId":"1","userName":"Administrator","dashboard":"0",
        // "role":["SA","admin","Auditor"]}}`
        UserInfo userInfo = new UserInfo();
        userInfo.userId = "1";
        userInfo.userName = "Administrator";
        userInfo.dashboard = "0";

        tokenInfo.token = "SCUI.Administrator.Auth";
        tokenInfo.userInfo = userInfo;
        tokenInfo.role = new String[] { "SA", "admin", "Auditor" };
        resData.setCode("200");
        resData.setData(tokenInfo);
        resData.setMessage("");
        return resData;
    }

    @RequestMapping(value = "/menu", method = { RequestMethod.GET, RequestMethod.POST })
    public ResData getMenu() {
        ResData resData = new ResData();
        String jsonString = "{\"menu\":[{\"name\":\"home\",\"path\":\"/project\",\"meta\":{\"title\":\"项目管理\",\"icon\":\"el-icon-eleme-filled\",\"type\":\"menu\"},\"children\":[{\"name\":\"project\",\"path\":\"/project\",\"meta\":{\"title\":\"项目列表\",\"icon\":\"el-icon-menu\",\"affix\":true}},{\"name\":\"construction\",\"path\":\"/construction\",\"meta\":{\"title\":\"工程列表\",\"icon\":\"el-icon-menu\",\"affix\":true}}]},{\"name\":\"userCenter\",\"path\":\"/usercenter\",\"meta\":{\"title\":\"帐号信息\",\"icon\":\"el-icon-user\",\"tag\":\"NEW\"},\"component\":\"userCenter\"},{\"name\":\"projectmanage\",\"path\":\"/projectmanage\",\"meta\":{\"title\":\"项目管理\",\"icon\":\"el-icon-takeaway-box\",\"type\":\"menu\"},\"children\":[{\"path\":\"/flowlist\",\"name\":\"flowlist\",\"meta\":{\"title\":\"项目列表\",\"icon\":\"el-icon-magic-stick\",\"type\":\"menu\"},\"component\":\"workflow/WorkFlowList\"},{\"path\":\"/flowedit\",\"name\":\"flowedit\",\"meta\":{\"title\":\"流程编辑\",\"icon\":\"el-icon-orange\",\"type\":\"menu\",\"hidden\":true},\"component\":\"workflow/WorkFlowEdit\"}]},{\"name\":\"setting\",\"path\":\"/setting\",\"meta\":{\"title\":\"设置\",\"icon\":\"el-icon-setting\",\"type\":\"menu\"},\"children\":[{\"path\":\"/flowlist\",\"name\":\"flowlist\",\"meta\":{\"title\":\"流程设置\",\"icon\":\"el-icon-magic-stick\",\"type\":\"menu\"},\"component\":\"workflow/WorkFlowList\"},{\"path\":\"/flowedit\",\"name\":\"flowedit\",\"meta\":{\"title\":\"流程编辑\",\"icon\":\"el-icon-orange\",\"type\":\"menu\",\"hidden\":true},\"component\":\"workflow/WorkFlowEdit\"},{\"path\":\"/setting/user\",\"name\":\"user\",\"meta\":{\"title\":\"用户管理\",\"icon\":\"el-icon-user-filled\",\"type\":\"menu\"},\"component\":\"setting/user\"},{\"path\":\"/setting/role\",\"name\":\"role\",\"meta\":{\"title\":\"角色管理\",\"icon\":\"el-icon-notebook\",\"type\":\"menu\"},\"component\":\"setting/role\"},{\"path\":\"/setting/organize\",\"name\":\"dept\",\"meta\":{\"title\":\"部门管理\",\"icon\":\"sc-icon-organization\",\"type\":\"menu\"},\"component\":\"setting/organize\"},{\"path\":\"/setting/business\",\"name\":\"business\",\"meta\":{\"title\":\"业务架构\",\"icon\":\"el-icon-scale-to-original\",\"type\":\"menu\"},\"component\":\"setting/business\"},{\"path\":\"/setting/dic\",\"name\":\"dic\",\"meta\":{\"title\":\"字典管理\",\"icon\":\"el-icon-document\",\"type\":\"menu\"},\"component\":\"setting/dic\"},{\"path\":\"/setting/table\",\"name\":\"tableSetting\",\"meta\":{\"title\":\"表格列管理\",\"icon\":\"el-icon-scale-to-original\",\"type\":\"menu\"},\"component\":\"setting/table\"},{\"path\":\"/setting/menu\",\"name\":\"settingMenu\",\"meta\":{\"title\":\"菜单管理\",\"icon\":\"el-icon-fold\",\"type\":\"menu\"},\"component\":\"setting/menu\"},{\"path\":\"/setting/task\",\"name\":\"task\",\"meta\":{\"title\":\"计划任务\",\"icon\":\"el-icon-alarm-clock\",\"type\":\"menu\"},\"component\":\"setting/task\"},{\"path\":\"/setting/client\",\"name\":\"client\",\"meta\":{\"title\":\"应用管理\",\"icon\":\"el-icon-help-filled\",\"type\":\"menu\"},\"component\":\"setting/client\"},{\"path\":\"/setting/log\",\"name\":\"log\",\"meta\":{\"title\":\"系统日志\",\"icon\":\"el-icon-warning\",\"type\":\"menu\"},\"component\":\"setting/log\"}]},{\"path\":\"/other/about\",\"name\":\"about\",\"meta\":{\"title\":\"关于\",\"icon\":\"el-icon-info-filled\",\"type\":\"menu\"},\"component\":\"other/about\"}],\"permissions\":[\"list.add\",\"list.edit\",\"list.delete\",\"user.add\",\"user.edit\",\"user.delete\"]}";
        Object group = JSON.parseObject(jsonString, Object.class);
        // ArrayList<Menu> menu = new ArrayList<Menu>();

        // {
        // Menu group = new Menu();
        // group.name = "home";
        // group.path = "/project";
        // group.meta = new Meta("项目管理", "el-icon-eleme-filled", "menu");
        // {
        // Menu group = new Menu();
        // group.name = "home";
        // group.path = "/project";
        // group.meta = new Meta("项目管理", "el-icon-eleme-filled", "menu");
        // }
        // menu.add(group)
        // }

        resData.setCode("200");
        resData.setData(group);
        resData.setMessage("");
        return resData;
    }

    @Data
    static class test {
        String title;
        String icon;
        String type;
        String menu;
    }

    @Data
    class UserInfo {
        String userId;
        String userName;
        String dashboard;
    }

    @Data
    class TokenInfo {
        String token;
        UserInfo userInfo;
        String[] role;

    }

    @Data
    class Meta {
        String title;
        String icon;
        String type;

        Meta(String t, String i, String tt) {
            title = t;
            icon = i;
            type = tt;
        }
    }

    @Data
    class Menu {
        String name;
        String path;
        Meta meta;
        Menu[] children;

    }
}
