package com.example.demo.controller.report;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.controller.common.BaseController;
import com.example.demo.controller.common.NeedLogin;
import com.example.demo.entity.Base;
import com.example.demo.entity.Project;
import com.example.demo.entity.actual.ActualDivision;
import com.example.demo.entity.budget.BudgetDivision;
import com.example.demo.entity.plan.PlanDivision;
import com.example.demo.service.IMyService;
import com.example.demo.service.IProjectService;
import com.example.demo.service.actual.IActualDivisionService;
import com.example.demo.service.budget.IBudgetDivisionService;
import com.example.demo.service.common.PageData;
import com.example.demo.service.common.SearchStr;
import com.example.demo.service.plan.IPlanDivisionService;
import com.example.demo.service.process.ITreeEntityNew;
import com.example.demo.service.process.ITreeService;
import com.example.demo.service.process.ITreeServiceConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/report/project")
public class ReportProjectController extends BaseController<BudgetDivision> {
    @Autowired
    private IBudgetDivisionService budgetDivisionService;
    @Autowired
    private IPlanDivisionService planDivisionService;
    @Autowired
    private IActualDivisionService actualDivisionService;
    @Autowired
    private ITreeService treeService;
    @Autowired
    private IProjectService projectService;

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


    @NeedLogin
    @GetMapping("/tree2")
    public ResData getTree2(HttpServletRequest request) {

        String ownId = request.getParameter("ownId"); // ??????????????????????????????where??????
        String projectId = request.getParameter("projectId"); //
        String cmd =
                request.getParameter("cmd") == null ? new String("") : request.getParameter("cmd"); //

        SearchStr searchStr = new SearchStr();
        searchStr.subject = request.getParameter("subject");
        searchStr.code = request.getParameter("code");
        searchStr.name = request.getParameter("name");
        searchStr.distinction = request.getParameter("distinction");
        searchStr.category = request.getParameter("category");

        if (ownId.equals("0")) {
            ownId = projectId;
        }
        List<BudgetDivision> prelist = null;
        List<Base> relist = null;

        String err = null;
        IPage userPage = null;
        PageData pageData = null;
        if (cmd.equals("sj")) { // ?????????????????????
            ITreeServiceConvert treeServiceConvert =
                    (Project project, List list) -> {
                        ActualDivision p1 = new ActualDivision();
                        p1.setOwnId(project.getOwnId());
                        p1.setParentId(project.getParentId());
                        p1.setSort(new BigDecimal(project.getSort()));
                        p1.setDivisionId(project.getProjectId());
                        p1.setProjectName(project.getProjectName());
                        p1.setSource("project");
                        p1.pushWorkAmount(new BigDecimal(0));
                        p1.pushCostSumprice(new BigDecimal(0));
                        p1.pushCostUnitprice(new BigDecimal(0));

                        p1.pushWorkAmount2(new BigDecimal(0));
                        p1.pushCostSumprice2(new BigDecimal(0));
                        p1.pushCostUnitprice2(new BigDecimal(0));

                        p1.pushWorkAmount3(new BigDecimal(0));
                        p1.pushCostSumprice3(new BigDecimal(0));
                        p1.pushCostUnitprice3(new BigDecimal(0));
                        list.add(p1);
                    };
            ITreeEntityNew<ActualDivision, PlanDivision> treeEntityNew =
                    (planDivision) -> {
                        ActualDivision budgetDivision = new ActualDivision();
                        budgetDivision.pushWorkAmount3(new BigDecimal(0));
                        budgetDivision.pushCostSumprice(new BigDecimal(0));
                        budgetDivision.pushWorkAmount(new BigDecimal(0));
                        budgetDivision.setSource("");
                        budgetDivision.setName(planDivision.getName());
                        budgetDivision.setSort(new BigDecimal(1));
                        budgetDivision.setParentId(planDivision.getParentId());
                        budgetDivision.setOwnId(planDivision.getOwnId());
                        budgetDivision.setDivisionId(planDivision.getDivisionId());
                        budgetDivision.setCategory(planDivision.getCategory());
                        budgetDivision.setCode(planDivision.getCode());
                        budgetDivision.setHave(planDivision.getHave());
                        budgetDivision.setUnit(planDivision.getUnit());
                        budgetDivision.setTag(planDivision.getTag());
                        budgetDivision.pushWorkAmount2(planDivision.fetchWorkAmount());
                        budgetDivision.pushCostUnitprice2(planDivision.fetchCostUnitprice());
                        budgetDivision.pushCostSumprice2(planDivision.fetchCostSumprice());
                        return budgetDivision;
                    };

            err = commonPreFetchCheck(request);

            if (err == null) {
                pageData =
                        ITreeService.<BudgetDivision, PlanDivision, ActualDivision>getTreeWithCompare(
                                projectId,
                                ownId,
                                actualDivisionService,
                                planDivisionService,
                                null,
                                projectService,
                                treeServiceConvert,
                                treeEntityNew,
                                null, searchStr);
            }
        } else if (cmd.equals("ys")) { // ?????????????????????
            ITreeServiceConvert treeServiceConvert =
                    (Project project, List list) -> {
                        BudgetDivision p1 = new BudgetDivision();
                        p1.setOwnId(project.getOwnId());
                        p1.setParentId(project.getParentId());
                        p1.setSort(new BigDecimal(project.getSort()));
                        p1.setDivisionId(project.getProjectId());
                        p1.setProjectName(project.getProjectName());
                        p1.setSource("project");
                        p1.pushWorkAmount(new BigDecimal(0));
                        p1.pushCostSumprice(new BigDecimal(0));
                        p1.pushCostUnitprice(new BigDecimal(0));

                        p1.pushWorkAmount2(new BigDecimal(0));
                        p1.pushCostSumprice2(new BigDecimal(0));
                        p1.pushCostUnitprice2(new BigDecimal(0));

                        p1.pushWorkAmount3(new BigDecimal(0));
                        p1.pushCostSumprice3(new BigDecimal(0));
                        p1.pushCostUnitprice3(new BigDecimal(0));
                        list.add(p1);
                    };
            ITreeEntityNew<BudgetDivision, PlanDivision> treeEntityNew =
                    (planDivision) -> {
                        BudgetDivision budgetDivision = new BudgetDivision();
                        budgetDivision.pushWorkAmount3(new BigDecimal(0));
                        budgetDivision.pushCostSumprice(new BigDecimal(0));
                        budgetDivision.pushWorkAmount(new BigDecimal(0));
                        budgetDivision.setSource("");
                        budgetDivision.setName(planDivision.getName());
                        budgetDivision.setSort(new BigDecimal(1));
                        budgetDivision.setParentId(planDivision.getParentId());
                        budgetDivision.setOwnId(planDivision.getOwnId());
                        budgetDivision.setDivisionId(planDivision.getDivisionId());
                        budgetDivision.setCategory(planDivision.getCategory());
                        budgetDivision.setCode(planDivision.getCode());
                        budgetDivision.setHave(planDivision.getHave());
                        budgetDivision.setUnit(planDivision.getUnit());
                        budgetDivision.setTag(planDivision.getTag());
                        budgetDivision.pushWorkAmount2(planDivision.fetchWorkAmount());
                        budgetDivision.pushCostUnitprice2(planDivision.fetchCostUnitprice());
                        budgetDivision.pushCostSumprice2(planDivision.fetchCostSumprice());
                        return budgetDivision;
                    };

            err = commonPreFetchCheck(request);

            if (err == null) {
                pageData =
                        ITreeService.<BudgetDivision, PlanDivision, ActualDivision>getTreeWithCompare(
                                projectId,
                                ownId,
                                budgetDivisionService,
                                planDivisionService,
                                null,
                                projectService,
                                treeServiceConvert,
                                treeEntityNew,
                                null, searchStr);
            }
        } else { // ????????????
            ITreeServiceConvert treeServiceConvert =
                    (Project project, List list) -> {
                        BudgetDivision p1 = new BudgetDivision();
                        p1.setOwnId(project.getOwnId());
                        p1.setParentId(project.getParentId());
                        p1.setSort(new BigDecimal(project.getSort()));
                        p1.setDivisionId(project.getProjectId());
                        p1.setProjectName(project.getProjectName());
                        p1.setSource("project");
                        p1.pushWorkAmount(new BigDecimal(0));
                        p1.pushCostSumprice(new BigDecimal(0));
                        p1.pushCostUnitprice(new BigDecimal(0));

                        p1.pushWorkAmount2(new BigDecimal(0));
                        p1.pushCostSumprice2(new BigDecimal(0));
                        p1.pushCostUnitprice2(new BigDecimal(0));

                        p1.pushWorkAmount3(new BigDecimal(0));
                        p1.pushCostSumprice3(new BigDecimal(0));
                        p1.pushCostUnitprice3(new BigDecimal(0));
                        list.add(p1);
                    };
            ITreeEntityNew<BudgetDivision, PlanDivision> treeEntityNew =
                    (planDivision) -> {
                        BudgetDivision budgetDivision = new BudgetDivision();
                        budgetDivision.pushWorkAmount3(new BigDecimal(0));
                        budgetDivision.pushCostSumprice(new BigDecimal(0));
                        budgetDivision.pushWorkAmount(new BigDecimal(0));
                        budgetDivision.setSource("");
                        budgetDivision.setName(planDivision.getName());
                        budgetDivision.setSort(new BigDecimal(1));
                        budgetDivision.setParentId(planDivision.getParentId());
                        budgetDivision.setOwnId(planDivision.getOwnId());
                        budgetDivision.setDivisionId(planDivision.getDivisionId());
                        budgetDivision.setCategory(planDivision.getCategory());
                        budgetDivision.setCode(planDivision.getCode());
                        budgetDivision.setHave(planDivision.getHave());
                        budgetDivision.setUnit(planDivision.getUnit());
                        budgetDivision.setTag(planDivision.getTag());
                        budgetDivision.pushWorkAmount2(planDivision.fetchWorkAmount());
                        budgetDivision.pushCostUnitprice2(planDivision.fetchCostUnitprice());
                        budgetDivision.pushCostSumprice2(planDivision.fetchCostSumprice());
                        return budgetDivision;
                    };
            ITreeEntityNew<BudgetDivision, ActualDivision> treeEntityNew2 =
                    (actualDivision) -> {
                        BudgetDivision budgetDivision = new BudgetDivision();
                        budgetDivision.pushWorkAmount3(new BigDecimal(0));
                        budgetDivision.pushCostSumprice(new BigDecimal(0));
                        budgetDivision.pushWorkAmount(new BigDecimal(0));
                        budgetDivision.setSource("");
                        budgetDivision.setName(actualDivision.getName());
                        budgetDivision.setSort(new BigDecimal(1));
                        budgetDivision.setParentId(actualDivision.getParentId());
                        budgetDivision.setOwnId(actualDivision.getOwnId());
                        budgetDivision.setDivisionId(actualDivision.getDivisionId());
                        budgetDivision.setCategory(actualDivision.getCategory());
                        budgetDivision.setCode(actualDivision.getCode());
                        budgetDivision.setHave(actualDivision.getHave());
                        budgetDivision.setUnit(actualDivision.getUnit());
                        budgetDivision.setTag(actualDivision.getTag());
                        budgetDivision.pushWorkAmount3(actualDivision.fetchWorkAmount());
                        budgetDivision.pushCostUnitprice3(actualDivision.fetchCostUnitprice());
                        budgetDivision.pushCostSumprice3(actualDivision.fetchCostSumprice());
                        return budgetDivision;
                    };
            err = commonPreFetchCheck(request);

            if (err == null) {
                pageData =
                        ITreeService.<BudgetDivision, PlanDivision, ActualDivision>getTreeWithCompare(
                                projectId,
                                ownId,
                                budgetDivisionService,
                                planDivisionService,
                                actualDivisionService,
                                projectService,
                                treeServiceConvert,
                                treeEntityNew,
                                treeEntityNew2, searchStr);
            }
        }
        ResData resData = new ResData();
        resData.setCode("200");
        if (err != null) {
            resData.setErr(err);
        }
        /////
        pageData.setExtend(getDictInfosDivison());
/////
        resData.setData(pageData);
        resData.setMessage("");
        return resData;
    }

    public class CompareTree {
    }
}
