package com.example.demo.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.document.Inventory;
import com.example.demo.entity.ListInventory;
import com.example.demo.entity.actual.ActualDivision;
import com.example.demo.entity.budget.BudgetDivision;
import com.example.demo.entity.plan.PlanDivision;
import com.example.demo.mapper.ListInventoryMapper;
import com.example.demo.service.actual.IActualDivisionService;
import com.example.demo.service.budget.IBudgetDivisionService;
import com.example.demo.service.inventory.IInventoryService;
import com.example.demo.service.plan.IPlanDivisionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class InventorySolr {

    @Autowired
    private ListInventoryMapper listInventoryMapper;
    @Autowired
    private IBudgetDivisionService budgetDivisionService;
    @Autowired
    private IPlanDivisionService planDivisionService;
    @Autowired
    private IActualDivisionService actualDivisionService;
    @Autowired
    private IInventoryService inventoryService;

    @Scheduled(cron = "0/2 * * * * ?")
    public void run() {


        QueryWrapper wrapper = new QueryWrapper();
        List orderColumn = new ArrayList<>();
        orderColumn.add("id");
        wrapper.orderBy(true, true, orderColumn);
        wrapper.last("limit 0,200");
        while (true) {
            List<ListInventory> list = listInventoryMapper.selectList(wrapper);
            if (list.size() == 0)
                break;
            Inventory inventory = new Inventory();
            //以后优化
            for (int i = 0; i < list.size(); i++) {
                ListInventory listInventory = list.get(i);
                IInventoryService.init2empty(inventory);
                if (listInventory.getTableType().equals("yusuan")) {
                    BudgetDivision budgetDivision = budgetDivisionService.getById(listInventory.getTableId());
                    IInventoryService.initFromBudget(budgetDivision, inventory);
                    inventoryService.save(inventory);
                } else if (listInventory.getTableType().equals("jihua")) {
                    PlanDivision planDivision = planDivisionService.getById(listInventory.getTableId());
                    IInventoryService.initFromPlan(planDivision, inventory);
                    inventoryService.save(inventory);
                } else {
                    ActualDivision actualDivision = actualDivisionService.getById(listInventory.getTableId());
                    IInventoryService.initFromActual(actualDivision, inventory);
                    inventoryService.save(inventory);
                }

                listInventoryMapper.deleteById(listInventory);
            }
        }

    }
}
