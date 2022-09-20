package com.example.demo.controller.plan;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.Base;
import com.example.demo.entity.Project;
import com.example.demo.entity.plan.PlanMeasure;
import com.example.demo.service.IMyService;
import com.example.demo.service.IProjectService;
import com.example.demo.service.plan.IPlanMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/plan/measure")
public class PlanMeasureController extends BaseController<PlanMeasure> {

  @Autowired private IPlanMeasureService planMeasureService;
  @Autowired private IProjectService projectService;

  @Override
  protected IMyService fetchService() {
    return planMeasureService;
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
  protected WrapperOpt fetchWrapper(HttpServletRequest request) {
    String ownId = request.getParameter("ownId") == null ? "" : request.getParameter("ownId");

    WrapperOpt wrapperOpt = new WrapperOpt();
    wrapperOpt.orderIsAsc = true;
    wrapperOpt.orderCondition = true;
    wrapperOpt.orderColumn = new ArrayList<>();
    wrapperOpt.orderColumn.add("sort");
    wrapperOpt.wheres = new HashMap<String, String>();
    wrapperOpt.wheres.put("own_id", ownId);
    return wrapperOpt;
  }

  private void convertProject2Measure(Project project, List list) {
    PlanMeasure p1 = new PlanMeasure();
    p1.setOwnId(project.getOwnId());
    p1.setParentId(project.getParentId());
    p1.setSort(new BigDecimal(project.getSort()));
    p1.setMeasureId(project.getProjectId());
    p1.setProjectName(project.getProjectName());
    p1.setSource("project");
    list.add(p1);
  }

  @GetMapping("/tree2")
  public ResData getTree2(HttpServletRequest request) {

    String ownId = request.getParameter("ownId"); // 只用树做对比不能用在where后面
    String selectId = request.getParameter("selectId"); //
    if (ownId.equals("0")) {
      ownId = selectId;
    }
    List<Base> prelist = null;
    List<Base> relist = null;
    int pageIndex = 1;
    String err = null;
    IPage userPage = null;
    err = commonPreFetchCheck(request);
    PageData pageData = null;
    if (err == null) {
      prelist = new ArrayList<Base>();
      relist = new ArrayList<>();
      // 把所有需要显示的项目相关id放入数组
      List instr = new ArrayList<String>();

      // 找到选中的，作为最大的那个节点
      Project project = projectService.getById(selectId);
      // 放入大树

      instr.add(project.getProjectId());
      // 从项目节点中找到选中节点下面的所有子节点
      // System.out.println(",,,,,33000,,,,," + project);
      Page page = new Page(pageIndex, 1000);
      WrapperOpt wrapperOpt = new WrapperOpt();
      wrapperOpt.orderIsAsc = true;
      wrapperOpt.orderCondition = true;
      wrapperOpt.orderColumn = new ArrayList<>();
      wrapperOpt.orderColumn.add("sort");
      wrapperOpt.wheres = new HashMap<String, String>();
      wrapperOpt.wheres.put("own_id", ownId);
      userPage = projectService.page(page, parseWrapperOption(wrapperOpt));
      List<Base> list = userPage.getRecords();
      // 树扁平化
      List<Base> tmplist = new ArrayList<Base>();
      for (int i = 0; i < list.size(); i++) {
        Base value = list.get(i);
        if (value.fetchParentId().toString().equals(selectId)) {
          list.remove(i);
          tmplist.add(value);
          projectService.treeBusinessParse(value, list, tmplist);

          i = -1;
        }
      }

      for (int i = 0; i < tmplist.size(); i++) {
        // System.out.println(",,,,,33,,,,," + tmplist.get(i));
        instr.add(tmplist.get(i).fetchPrimeId());
        // 放入大数组
        convertProject2Measure((Project) tmplist.get(i), prelist);
      }

      // 去查division
      Page page2 = new Page(pageIndex, 1000);
      WrapperOpt wrapperOpt2 = new WrapperOpt();
      wrapperOpt2.orderIsAsc = true;
      wrapperOpt2.orderCondition = true;
      wrapperOpt2.orderColumn = new ArrayList<>();
      wrapperOpt2.orderColumn.add("sort");
      if (instr.size() > 0) {
        wrapperOpt2.ins = new HashMap<String, List<String>>();
        wrapperOpt2.ins.put("own_id", instr);
      }
      userPage = planMeasureService.page(page, parseWrapperOption(wrapperOpt2));
      List<PlanMeasure> list2 = userPage.getRecords();
      // 放入大数组
      prelist.addAll(list2);
      // 做树形渲染
      for (int i = 0; i < prelist.size(); i++) {
        Base value = prelist.get(i);
        if (value.fetchParentId().toString().equals(selectId)) {
          prelist.remove(i);
          relist.add(planMeasureService.treeBusinessParse(value, prelist));
          i = -1;
        }
      }
      List<PlanMeasure> resist = new ArrayList<PlanMeasure>();
      convertProject2Measure(project, resist);
      resist.get(0).setChildren(relist);
      pageData = new PageData();
      pageData.setItemTotal(userPage.getTotal());
      pageData.setPageSize(userPage.getSize());
      pageData.setList(resist);
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
}
