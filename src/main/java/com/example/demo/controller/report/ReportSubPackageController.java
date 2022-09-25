package com.example.demo.controller.report;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.Base;
import com.example.demo.entity.Project;
import com.example.demo.entity.report.TotalDivision;
import com.example.demo.service.IMyService;
import com.example.demo.service.IProjectService;
import com.example.demo.service.actual.machine.IActualDivisionMachineService;
import com.example.demo.service.common.PageData;
import com.example.demo.service.common.WrapperOpt;
import com.example.demo.service.process.ITreeService;
import com.example.demo.service.process.ITreeServiceConvert;
import com.example.demo.service.report.ITotalDivisionService;
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
@RequestMapping("/report/subpackage")
public class ReportSubPackageController extends BaseController<TotalDivision> {
  @Autowired private ITotalDivisionService totalDivisionService;
  @Autowired private IActualDivisionMachineService actualDivisionMachineService;
  @Autowired private ITreeService treeService;
  @Autowired private IProjectService projectService;

  @Override
  protected IMyService fetchService() {
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

  @GetMapping("/tree2")
  public ResData getTree2(HttpServletRequest request) {

    String ownId = request.getParameter("ownId"); // 只用树做对比不能用在where后面
    ownId = "";
    String selectId = request.getParameter("selectId"); //

    List<Base> prelist = null;
    List<Base> relist = null;
    int pageIndex = 1;
    String err = null;
    IPage userPage = null;

    ITreeServiceConvert treeServiceConvert =
        (Project project, List list) -> {
          TotalDivision p1 = new TotalDivision();
          p1.setOwnId(project.getOwnId());
          p1.setParentId(project.getParentId());
          p1.setSort(new BigDecimal(project.getSort()));
          p1.setDivisionId(project.getProjectId());
          p1.setProjectName(project.getProjectName());
          p1.setSource("project");

          list.add(p1);
        };

    err = commonPreFetchCheck(request);
    PageData pageData = null;
    if (err == null) {
      pageData =
          ITreeService.<TotalDivision>getTreeWithPriceByPackage(
              selectId,
              ownId,
              totalDivisionService,
              actualDivisionMachineService,
              treeServiceConvert);
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
