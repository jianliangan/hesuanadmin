package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.controller.common.BaseController;
import com.example.demo.service.IMyService;
import com.example.demo.service.common.WrapperOpt;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/system")
public class SystemController extends BaseController {
  @Override
  protected WrapperOpt fetchWrapper(HttpServletRequest request) {
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

  @RequestMapping(
      value = "/token",
      method = {RequestMethod.GET, RequestMethod.POST})
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
    tokenInfo.role = new String[] {"SA", "admin", "Auditor"};
    resData.setCode("200");
    resData.setData(tokenInfo);
    resData.setMessage("");
    return resData;
  }

  @RequestMapping(
      value = "/menu",
      method = {RequestMethod.GET, RequestMethod.POST})
  public ResData getMenu() {
    ResData resData = new ResData();
    String jsonString =
        "{\"menu\":[{\"name\":\"home\",\"path\":\"/home\",\"meta\":{\"title\":\"项目管理\",\"icon\":\"el-icon-eleme-filled\",\"type\":\"menu\"},\"children\":[{\"name\":\"homeproject\",\"path\":\"/home/project\",\"meta\":{\"title\":\"项目列表\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"home/project/index\"},{\"name\":\"homeconstruction\",\"path\":\"/home/construction\",\"meta\":{\"title\":\"工程列表\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"home/construction/index\"},{\"name\":\"homeprojectindex\",\"path\":\"/home/projectindex\",\"meta\":{\"title\":\"项目索引\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"home/projectindex/index\"}]},{\"name\":\"budget\",\"path\":\"/budget\",\"meta\":{\"title\":\"收入预算管理\",\"icon\":\"el-icon-eleme-filled\",\"type\":\"menu\"},\"children\":[{\"name\":\"budgetdivision\",\"path\":\"/budget/division\",\"meta\":{\"title\":\"分部分项管理\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"budget/division/index\"},{\"name\":\"budgetmeasure\",\"path\":\"/budget/measure\",\"meta\":{\"title\":\"措施分项管理\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"budget/measure/index\"},{\"name\":\"budgetother\",\"path\":\"/budget/other\",\"meta\":{\"title\":\"其他费用管理\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"budget/other/index\"}]},{\"name\":\"plan\",\"path\":\"/plan\",\"meta\":{\"title\":\"成本计划\",\"icon\":\"el-icon-eleme-filled\",\"type\":\"menu\"},\"children\":[{\"name\":\"plandivision\",\"path\":\"/plan/division\",\"meta\":{\"title\":\"分部分项计划管理\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"plan/division/index\"},{\"name\":\"planmeasure\",\"path\":\"/plan/measure\",\"meta\":{\"title\":\"措施分项计划管理\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"plan/measure/index\"},{\"name\":\"planother\",\"path\":\"/plan/other\",\"meta\":{\"title\":\"其他费用计划管理\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"plan/other/index\"}]},{\"name\":\"actual\",\"path\":\"/actual\",\"meta\":{\"title\":\"实际清单管理\",\"icon\":\"el-icon-eleme-filled\",\"type\":\"menu\"},\"children\":[{\"name\":\"actualdivision\",\"path\":\"/actual/division\",\"meta\":{\"title\":\"分部分项计划管理\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"actual/division/index\"},{\"name\":\"actualmeasure\",\"path\":\"/actual/measure\",\"meta\":{\"title\":\"措施分项计划管理\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"actual/measure/index\"},{\"name\":\"actualother\",\"path\":\"/actual/other\",\"meta\":{\"title\":\"其他费用计划管理\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"actual/other/index\"}]},{\"name\":\"report\",\"path\":\"/report\",\"meta\":{\"title\":\"报表分析\",\"icon\":\"el-icon-eleme-filled\",\"type\":\"menu\"},\"children\":[{\"name\":\"reportproject\",\"path\":\"/report/project\",\"meta\":{\"title\":\"单位工程对比\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"report/project/index\"},{\"name\":\"reportsubpackage\",\"path\":\"/report/subpackage\",\"meta\":{\"title\":\"分包管理分析\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"report/subpackage/index\"},{\"name\":\"reportactual\",\"path\":\"/report/actual\",\"meta\":{\"title\":\"实际与计划对比分析\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"report/actual/index\"},{\"name\":\"reportbudget\",\"path\":\"/report/budget\",\"meta\":{\"title\":\"预算与计划对比分析\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"report/budget/index\"},{\"name\":\"reportplan\",\"path\":\"/report/plan\",\"meta\":{\"title\":\"单位工程与计划的进度图分析\",\"icon\":\"el-icon-menu\",\"affix\":true},\"component\":\"report/plan/index\"}]},{\"name\":\"userCenter\",\"path\":\"/usercenter\",\"meta\":{\"title\":\"帐号信息\",\"icon\":\"el-icon-user\",\"tag\":\"NEW\"},\"component\":\"userCenter\"},{\"name\":\"projectmanage\",\"path\":\"/projectmanage\",\"meta\":{\"title\":\"项目管理\",\"icon\":\"el-icon-takeaway-box\",\"type\":\"menu\"},\"children\":[{\"path\":\"/flowlist\",\"name\":\"flowlist\",\"meta\":{\"title\":\"项目列表\",\"icon\":\"el-icon-magic-stick\",\"type\":\"menu\"},\"component\":\"workflow/WorkFlowList\"},{\"path\":\"/flowedit\",\"name\":\"flowedit\",\"meta\":{\"title\":\"流程编辑\",\"icon\":\"el-icon-orange\",\"type\":\"menu\",\"hidden\":true},\"component\":\"workflow/WorkFlowEdit\"}]},{\"name\":\"setting\",\"path\":\"/setting\",\"meta\":{\"title\":\"设置\",\"icon\":\"el-icon-setting\",\"type\":\"menu\"},\"children\":[{\"path\":\"/flowlist\",\"name\":\"flowlist\",\"meta\":{\"title\":\"流程设置\",\"icon\":\"el-icon-magic-stick\",\"type\":\"menu\"},\"component\":\"workflow/WorkFlowList\"},{\"path\":\"/flowedit\",\"name\":\"flowedit\",\"meta\":{\"title\":\"流程编辑\",\"icon\":\"el-icon-orange\",\"type\":\"menu\",\"hidden\":true},\"component\":\"workflow/WorkFlowEdit\"},{\"path\":\"/setting/user\",\"name\":\"user\",\"meta\":{\"title\":\"用户管理\",\"icon\":\"el-icon-user-filled\",\"type\":\"menu\"},\"component\":\"setting/user\"},{\"path\":\"/setting/role\",\"name\":\"role\",\"meta\":{\"title\":\"角色管理\",\"icon\":\"el-icon-notebook\",\"type\":\"menu\"},\"component\":\"setting/role\"},{\"path\":\"/setting/organize\",\"name\":\"dept\",\"meta\":{\"title\":\"部门管理\",\"icon\":\"sc-icon-organization\",\"type\":\"menu\"},\"component\":\"setting/organize\"},{\"path\":\"/setting/business\",\"name\":\"business\",\"meta\":{\"title\":\"业务架构\",\"icon\":\"el-icon-scale-to-original\",\"type\":\"menu\"},\"component\":\"setting/business\"},{\"path\":\"/setting/dic\",\"name\":\"dic\",\"meta\":{\"title\":\"字典管理\",\"icon\":\"el-icon-document\",\"type\":\"menu\"},\"component\":\"setting/dic\"},{\"path\":\"/setting/table\",\"name\":\"tableSetting\",\"meta\":{\"title\":\"表格列管理\",\"icon\":\"el-icon-scale-to-original\",\"type\":\"menu\"},\"component\":\"setting/table\"},{\"path\":\"/setting/menu\",\"name\":\"settingMenu\",\"meta\":{\"title\":\"菜单管理\",\"icon\":\"el-icon-fold\",\"type\":\"menu\"},\"component\":\"setting/menu\"},{\"path\":\"/setting/task\",\"name\":\"task\",\"meta\":{\"title\":\"计划任务\",\"icon\":\"el-icon-alarm-clock\",\"type\":\"menu\"},\"component\":\"setting/task\"},{\"path\":\"/setting/client\",\"name\":\"client\",\"meta\":{\"title\":\"应用管理\",\"icon\":\"el-icon-help-filled\",\"type\":\"menu\"},\"component\":\"setting/client\"},{\"path\":\"/setting/log\",\"name\":\"log\",\"meta\":{\"title\":\"系统日志\",\"icon\":\"el-icon-warning\",\"type\":\"menu\"},\"component\":\"setting/log\"}]},{\"path\":\"/other/about\",\"name\":\"about\",\"meta\":{\"title\":\"关于\",\"icon\":\"el-icon-info-filled\",\"type\":\"menu\"},\"component\":\"other/about\"}],\"permissions\":[\"list.add\",\"list.edit\",\"list.delete\",\"user.add\",\"user.edit\",\"user.delete\"]}";
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
