package com.example.demo.controller.budget.machine;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.common.BaseController;
import com.example.demo.controller.common.NeedLogin;
import com.example.demo.entity.LockTable;
import com.example.demo.entity.budget.machine.BudgetDivisionMachine;
import com.example.demo.service.ILockTableService;
import com.example.demo.service.IMyService;
import com.example.demo.service.budget.machine.IBudgetDivisionMachineService;
import com.example.demo.service.common.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/budget/division/machine")
public class BudgetDivisionMachineController extends BaseController<BudgetDivisionMachine> {

    @Autowired
    private IBudgetDivisionMachineService budgetDivisionMachineService;
    @Autowired
    ILockTableService lockTableService;

    @Override
    protected IMyService fetchService() {
        return budgetDivisionMachineService;
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
    @GetMapping("/treewith")
    public ResData getTreeWith(HttpServletRequest request) {
        ResData resData = getGeneral(request);
        PageData pageData = (PageData) resData.getData();
/////
        pageData.setExtend(getDictInfosMachine());
/////
        resData.setData(pageData);
        resData.setMessage("");
        return resData;
    }

    public void filterUpdateRow(String pre, String cmd, BudgetDivisionMachine row, HttpServletRequest request) {

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
