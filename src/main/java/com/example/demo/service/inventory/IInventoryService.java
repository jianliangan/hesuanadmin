package com.example.demo.service.inventory;

import com.example.demo.document.Inventory;
import com.example.demo.entity.actual.ActualDivision;
import com.example.demo.entity.budget.BudgetDivision;
import com.example.demo.entity.plan.PlanDivision;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.Map;

public interface IInventoryService {
    QueryResponse getList(Map<String, String> qparams);

    void save(Inventory inventory);

    static void initFromBudget(BudgetDivision budgetDivision, Inventory inventory) {
        if (budgetDivision == null)
            return;
        inventory.setId("yusuan:" + budgetDivision.getDivisionId());
        inventory.setCode(budgetDivision.getCode());
        inventory.setSubject(budgetDivision.getSubject());
        inventory.setType("yusuan");
        inventory.setUnit(budgetDivision.getUnit());
        inventory.setWorkAmount(budgetDivision.getWorkAmount().doubleValue());
        inventory.setCategory(budgetDivision.getCategory());
        inventory.setHave(budgetDivision.getHave().doubleValue());
        inventory.setName(budgetDivision.getName());
        inventory.setDistinction(budgetDivision.getDistinction());
        ///// 综合价格
        inventory.setCostUnitprice(budgetDivision.getCostUnitprice().doubleValue());
        inventory.setCostSumprice(budgetDivision.getCostSumprice().doubleValue());
        /// 管理费，
        inventory.setManageUnitprice(budgetDivision.getManageUnitprice().doubleValue());
        inventory.setManageSumprice(budgetDivision.getManageSumprice().doubleValue());
        ///利润
        inventory.setProfitUnitprice(budgetDivision.getProfitUnitprice().doubleValue());
        inventory.setProfitSumprice(budgetDivision.getProfitSumprice().doubleValue());
    }

    static void initFromPlan(PlanDivision planDivision, Inventory inventory) {
        if (planDivision == null) return;
        inventory.setId("jihua:" + planDivision.getDivisionId());
        inventory.setCode(planDivision.getCode());
        inventory.setSubject(planDivision.getSubject());
        inventory.setType("jihua");
        inventory.setUnit(planDivision.getUnit());
        inventory.setWorkAmount(planDivision.getWorkAmount().doubleValue());
        inventory.setCategory(planDivision.getCategory());
        inventory.setHave(planDivision.getHave().doubleValue());
        inventory.setName(planDivision.getName());
        inventory.setDistinction(planDivision.getDistinction());
        /////成本单价和合价
        inventory.setCostUnitprice(planDivision.getCostUnitprice().doubleValue());
        inventory.setCostSumprice(planDivision.getCostSumprice().doubleValue());
        //// 人工，材料，机械，设备，分包费
        inventory.setCostManprice(planDivision.getCostManprice().doubleValue());
        inventory.setCostMaterialsprice(planDivision.getCostMaterialsprice().doubleValue());
        inventory.setCostMechanicsprice(planDivision.getCostMechanicsprice().doubleValue());
        inventory.setCostDeviceprice(planDivision.getCostDeviceprice().doubleValue());
        inventory.setCostSubpackageprice(planDivision.getCostSubpackageprice().doubleValue());

    }

    static void initFromActual(ActualDivision actualDivision, Inventory inventory) {
        if (actualDivision == null) return;
        inventory.setId("shiji:" + actualDivision.getDivisionId());
        inventory.setCode(actualDivision.getCode());
        inventory.setSubject(actualDivision.getSubject());
        inventory.setType("shiji");
        inventory.setUnit(actualDivision.getUnit());
        inventory.setWorkAmount(actualDivision.getWorkAmount().doubleValue());
        inventory.setCategory(actualDivision.getCategory());
        inventory.setHave(actualDivision.getHave().doubleValue());
        inventory.setName(actualDivision.getName());
        inventory.setDistinction(actualDivision.getDistinction());

        /////成本单价和合价
        inventory.setCostUnitprice(actualDivision.getCostUnitprice().doubleValue());
        inventory.setCostSumprice(actualDivision.getCostSumprice().doubleValue());
        //// 人工，材料，机械，设备，分包费
        inventory.setCostManprice(actualDivision.getCostManprice().doubleValue());
        inventory.setCostMaterialsprice(actualDivision.getCostMaterialsprice().doubleValue());
        inventory.setCostMechanicsprice(actualDivision.getCostMechanicsprice().doubleValue());
        inventory.setCostDeviceprice(actualDivision.getCostDeviceprice().doubleValue());
        inventory.setCostSubpackageprice(actualDivision.getCostSubpackageprice().doubleValue());
    }

    static void init2empty(Inventory inventory) {
        inventory.setId("");
        inventory.setCode("");
        inventory.setSubject("");
        inventory.setType("");
        inventory.setUnit("");
        inventory.setWorkAmount((double) 0);
        inventory.setCategory("");
        inventory.setHave((double) 0);
        inventory.setName("");
        ///// 综合价格
        inventory.setCostUnitprice(0D);
        inventory.setCostSumprice(0D);
        /// 管理费，d
        inventory.setManageUnitprice(0D);
        inventory.setManageSumprice(0D);
        ///利润
        inventory.setProfitUnitprice(0D);
        inventory.setProfitSumprice(0D);
        //////////
        inventory.setCostManprice(0D);
        inventory.setCostMaterialsprice(0D);
        inventory.setCostMechanicsprice(0D);
        inventory.setCostDeviceprice(0D);
        inventory.setCostSubpackageprice(0D);
    }
}
