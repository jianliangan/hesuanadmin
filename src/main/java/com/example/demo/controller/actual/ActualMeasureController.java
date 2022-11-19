package com.example.demo.controller.actual;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.controller.common.BaseController;
import com.example.demo.controller.common.NeedLogin;
import com.example.demo.entity.Base;
import com.example.demo.entity.LockTable;
import com.example.demo.entity.Project;
import com.example.demo.entity.actual.ActualMeasure;
import com.example.demo.entity.plan.PlanMeasure;
import com.example.demo.mapper.actual.machine.ActualMeasureMachineMapper;
import com.example.demo.service.ILockTableService;
import com.example.demo.service.IMyService;
import com.example.demo.service.IProjectService;
import com.example.demo.service.actual.IActualMeasureService;
import com.example.demo.service.common.PageData;
import com.example.demo.service.common.SearchStr;
import com.example.demo.service.process.ITreeService;
import com.example.demo.service.process.ITreeServiceConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/actual/measure")
public class ActualMeasureController extends BaseController<ActualMeasure> {

    @Autowired
    private IActualMeasureService actualMeasureService;
    @Autowired
    private IProjectService projectService;
    @Autowired
    private ITreeService treeService;
    @Autowired
    ILockTableService lockTableService;
    @Autowired
    ActualMeasureMachineMapper actualMeasureMachineMapper;

    @Override
    protected IMyService fetchService() {
        return actualMeasureService;
    }

    @Override
    protected String commonPrePushCheck(HttpServletRequest request) {
        return null;
    }

    @Override
    protected String commonPreFetchCheck(HttpServletRequest request) {
        return null;
    }

    public void filterUpdateRow(String pre, String cmd, ActualMeasure row, HttpServletRequest request) {
        QueryWrapper wrapper = new QueryWrapper();
        Map wheres = new HashMap<String, String>();
        wheres.put("id", "budgetall");
        wrapper.allEq(wheres);
        wrapper.last("for update");
        LockTable lockTable = lockTableService.getOne(wrapper);
        final Long[] sortBudget = {lockTable.getBudget() + 1};
        if (row == null)
            return;
        if (row.getCode() == null || row.getCode().length() == 0) {


            row.setHave(new BigDecimal(0));
            row.setWorkAmount(new BigDecimal(0));
            row.setCostUnitprice(new BigDecimal(0));
            row.setCostSumprice(new BigDecimal(0));

            row.setCostManprice(new BigDecimal(0));
            row.setCostMaterialsprice(new BigDecimal(0));
            row.setCostMechanicsprice(new BigDecimal(0));
            row.setCostDeviceprice(new BigDecimal(0));
            row.setCostSubpackageprice(new BigDecimal(0));
        }
        if (pre.equals("pre")) {
            if (cmd.equals("add")) {
                IMyService.commonComputPrice(actualMeasureMachineMapper, row, false, "actual");
                BigDecimal tmp = row.getSort();
                if (new BigDecimal(tmp.longValue()).equals(tmp)) {//如果是整数的需要后端从新拿一个sort
                    row.setSort(new BigDecimal(sortBudget[0]));
                }
            } else if (cmd.equals("edit")) {
                IMyService.commonComputPrice(actualMeasureMachineMapper, row, true, "actual");
                if (row.getCode() == null || row.getCode() == "") {
                    row.setWorkAmount(new BigDecimal(0));
                    row.setCostUnitprice(new BigDecimal(0));
                    row.setCostSumprice(new BigDecimal(0));
                }
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
    @GetMapping("/tree2")
    public ResData getTree2(HttpServletRequest request) {

        String ownId = request.getParameter("ownId"); // 只用树做对比不能用在where后面
        String projectId = request.getParameter("projectId"); //

        SearchStr searchStr = new SearchStr();
        searchStr.subject = request.getParameter("subject");
        searchStr.code = request.getParameter("code");
        searchStr.name = request.getParameter("name");
        searchStr.distinction = request.getParameter("distinction");
        searchStr.category = request.getParameter("category");

        if (ownId.equals("0")) {
            ownId = projectId;
        }
        List<Base> prelist = null;
        List<Base> relist = null;
        int pageIndex = 1;
        String err = null;
        IPage userPage = null;
        ITreeServiceConvert treeServiceConvert =
                (Project project, List list) -> {
                    PlanMeasure p1 = new PlanMeasure();
                    p1.setOwnId(project.getOwnId());
                    p1.setParentId(project.getParentId());
                    p1.setSort(new BigDecimal(project.getSort()));
                    p1.setMeasureId(project.getProjectId());
                    p1.setProjectName(project.getProjectName());
                    p1.setSource("project");
                    p1.pushWorkAmount(new BigDecimal(0));
                    p1.pushCostSumprice(new BigDecimal(0));
                    p1.pushCostUnitprice(new BigDecimal(0));
                    list.add(p1);
                };
        err = commonPreFetchCheck(request);
        PageData pageData = null;

        if (err == null) {
            pageData =
                    ITreeService.<ActualMeasure>getTreeWithPrice(
                            projectId, ownId, actualMeasureService, projectService, treeServiceConvert, searchStr);
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
}
