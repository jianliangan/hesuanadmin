package com.example.demo.controller.plan.machine;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.Base;
import com.example.demo.entity.LockTable;
import com.example.demo.entity.dict.SubPackage;
import com.example.demo.entity.dict.SupplyUnit;
import com.example.demo.entity.plan.machine.PlanDivisionMachine;
import com.example.demo.service.ILockTableService;
import com.example.demo.service.IMyService;
import com.example.demo.service.common.PageData;
import com.example.demo.service.dict.ISubPackageService;
import com.example.demo.service.dict.ISupplyUnitService;
import com.example.demo.service.plan.machine.IPlanDivisionMachineService;
import com.example.demo.service.process.ITreeService;
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
@RequestMapping("/plan/division/machine")
public class PlanDivisionMachineController extends BaseController<PlanDivisionMachine> {

    @Autowired
    private IPlanDivisionMachineService planDivisionMachineService;
    @Autowired
    private ISubPackageService subPackageService;
    @Autowired
    private ISupplyUnitService supplyUnitService;
    @Autowired
    ILockTableService lockTableService;

    @Override
    protected IMyService fetchService() {
        return planDivisionMachineService;
    }

    @Override
    protected String commonPrePushCheck(HttpServletRequest request) {

        return null;
    }

    @Override
    protected String commonPreFetchCheck(HttpServletRequest request) {

        return null;
    }


    @GetMapping("/tree")
    public ResData getTree(HttpServletRequest request) {
        if (fetchService() == null) {
            return null;
        }
        String rootId = request.getParameter("rootId"); // 只用树做对比不能用在where后面
        List<PlanDivisionMachine> relist = null;
        int pageIndex = 1;
        String err = null;
        IPage userPage = null;
        err = commonPreFetchCheck(request);
        PageData pageData = null;
        if (err == null) {

            // Page<PlanDivisionMachine> page = new Page(pageIndex, 5000);
            QueryWrapper wrapper = fetchWrapper(request);

            //userPage = fetchService().page(page, wrapper);

            // userPage.getRecords().forEach(System.out::println);
            List<Base> list = fetchService().list(wrapper);//userPage.getRecords();

            // 拿到分包商信息转成map
            List instr = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                PlanDivisionMachine value = (PlanDivisionMachine) list.get(i);
                instr.add(value.getSubPackageId());
            }

            QueryWrapper wrapper2 = new QueryWrapper();
            if (instr.size() > 0)
                wrapper2.in("sub_package_id", instr.toArray());
            List<SubPackage> subPackageList =
                    subPackageService.list(wrapper2);
            Map<String, SubPackage> subPackageMap = new HashMap<String, SubPackage>();
            for (int i = 0; i < subPackageList.size(); i++) {
                subPackageMap.put(subPackageList.get(i).getSubPackageId(), subPackageList.get(i));
            }
            // 拿到供应单位信息转成map
            List instr2 = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                PlanDivisionMachine value = (PlanDivisionMachine) list.get(i);
                instr2.add(value.getSupplyUnitId());
            }
            QueryWrapper wrapper3 = new QueryWrapper();
            if (instr2.size() > 0)
                wrapper3.in("supply_unit_id", instr2.toArray());
            List<SupplyUnit> supplyUnitList =
                    supplyUnitService.list(wrapper3);
            Map<String, SupplyUnit> supplyUnitMap = new HashMap<String, SupplyUnit>();
            for (int i = 0; i < supplyUnitList.size(); i++) {
                supplyUnitMap.put(supplyUnitList.get(i).getSupplyUnitId(), supplyUnitList.get(i));
            }

            for (int i = 0; i < list.size(); i++) {
                PlanDivisionMachine value = (PlanDivisionMachine) list.get(i);
                SubPackage tmp = subPackageMap.get(value.getSubPackageId());
                if (tmp != null) value.setSubPackageName(tmp.getSubPackageName());

                SupplyUnit tmp2 = supplyUnitMap.get(value.getSupplyUnitId());
                if (tmp2 != null) value.setSupplyUnitName(tmp2.getSupplyUnitName());
            }
            int itemOffset = (pageIndex - 1) * pageSize;

            // 树形

            relist = new ArrayList<PlanDivisionMachine>();
            for (int i = 0; i < list.size(); i++) {
                PlanDivisionMachine value = (PlanDivisionMachine) list.get(i);
                if (value.fetchParentId().toString().equals(rootId)) {
                    list.remove(i);
                    relist.add((PlanDivisionMachine) ITreeService.treeLoop0(value, (List<Base>) list));
                    i = -1;
                }
            }

            pageData = new PageData();
            pageData.setItemTotal(1);
            pageData.setPageSize(1);
            pageData.setList(relist);
        }
        ResData resData = new ResData();
        resData.setCode("200");
        if (err != null) {
            resData.setErr(err);
        }
        /////
        pageData.setExtend(getDictInfosMachine());
/////
        resData.setData(pageData);
        resData.setMessage("");
        return resData;
    }

    public void filterUpdateRow(String pre, String cmd, PlanDivisionMachine row, HttpServletRequest request) {

        if (row == null)
            return;
        QueryWrapper wrapper = new QueryWrapper();
        Map wheres = new HashMap<String, String>();
        wheres.put("id", "budgetall");
        wrapper.allEq(wheres);
        wrapper.last("for update");
        LockTable lockTable = lockTableService.getOne(wrapper);
        if (pre.equals("pre")) {
            row.setCombinedPrice(row.getPrice().multiply(row.getCount()));
            if (cmd.equals("add")) {

            } else if (cmd.equals("edit")) {


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
