package com.example.demo.service.process;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Base;
import com.example.demo.entity.Project;
import com.example.demo.entity.actual.ActualDivision;
import com.example.demo.entity.budget.BudgetDivision;
import com.example.demo.entity.plan.PlanDivision;
import com.example.demo.service.IMyService;
import com.example.demo.service.IProjectService;
import com.example.demo.service.common.ISumReportCompareService;
import com.example.demo.service.common.ISumReportService;
import com.example.demo.service.common.PageData;
import com.example.demo.service.common.WrapperOpt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ITreeService {
  public static <T extends Base & ISumReportService> PageData getTreeWithPrice(
      String selectId,
      String ownId,
      IMyService myService,
      IProjectService projectService,
      ITreeServiceConvert treeServiceConvert) {

    ArrayList<T> prelist = new ArrayList<T>();
    ArrayList<Base> relist = new ArrayList<Base>();
    // 把所有需要显示的项目相关id放入数组
    List instr = new ArrayList<String>();
    int pageIndex = 1;
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
    Page userPage = projectService.page(page, WrapperOpt.parseWrapperOption(wrapperOpt));
    List<Base> list = userPage.getRecords();
    // 树扁平化
    List<Base> tmplist = new ArrayList<Base>();
    for (int i = 0; i < list.size(); i++) {
      Base value = list.get(i);
      if (value.fetchParentId().toString().equals(selectId)) {
        list.remove(i);
        tmplist.add(value);
        treeLoop0(value, list, tmplist);

        i = -1;
      }
    }

    for (int i = 0; i < tmplist.size(); i++) {

      instr.add(tmplist.get(i).fetchPrimeId());
      // 放入大数组
      treeServiceConvert.convertProject2((Project) tmplist.get(i), prelist);
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
    userPage = (Page) myService.page(page, WrapperOpt.parseWrapperOption(wrapperOpt2));
    List<T> list2 = userPage.getRecords();

    // 放入大数组
    prelist.addAll(list2);
    List<T> resist = new ArrayList<T>();
    treeServiceConvert.convertProject2(project, resist);
    // 做树形渲染
    treeLoop1((T) resist.get(0), prelist);
    PageData pageData = new PageData();
    pageData.setItemTotal(userPage.getTotal());
    pageData.setPageSize(userPage.getSize());
    pageData.setList(resist);

    return pageData;
  }

  public static PageData getTreeWithCompare(
      String selectId,
      String ownId,
      IMyService budgetService,
      IMyService planService,
      IMyService actualService,
      IProjectService projectService,
      ITreeServiceConvert treeServiceConvert) {

    ArrayList<BudgetDivision> prelist = new ArrayList<BudgetDivision>();
    ArrayList<Base> relist = new ArrayList<Base>();
    // 把所有需要显示的项目相关id放入数组
    List instr = new ArrayList<String>();
    int pageIndex = 1;
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
    Page userPage = projectService.page(page, WrapperOpt.parseWrapperOption(wrapperOpt));
    List<Base> list = userPage.getRecords();
    // 项目树扁平化
    List<Base> tmplist = new ArrayList<Base>();
    for (int i = 0; i < list.size(); i++) {
      Base value = list.get(i);
      if (value.fetchParentId().toString().equals(selectId)) {
        list.remove(i);
        tmplist.add(value);
        ITreeService.treeLoop0(value, list, tmplist);

        i = -1;
      }
    }
    // 找到所有子id
    for (int i = 0; i < tmplist.size(); i++) {

      instr.add(tmplist.get(i).fetchPrimeId());
      // 把项目信息转成输出格式并放入大数组
      treeServiceConvert.convertProject2((Project) tmplist.get(i), prelist);
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
    // 预算
    userPage = (Page) budgetService.page(page, WrapperOpt.parseWrapperOption(wrapperOpt2));
    List<BudgetDivision> listBudget = userPage.getRecords();
    Map<Object, BudgetDivision> compareList = new HashMap<Object, BudgetDivision>();

    ArrayList<BudgetDivision> listBudgetCpy = new ArrayList<BudgetDivision>();

    for (int i = 0; i < listBudget.size(); i++) {
      compareList.put(listBudget.get(i).fetchPrimeId(), listBudget.get(i));
    }
    // 计划
    userPage = (Page) planService.page(page, WrapperOpt.parseWrapperOption(wrapperOpt2));
    List<PlanDivision> listPlan = userPage.getRecords();

    for (int i = 0; i < listPlan.size(); i++) {
      BudgetDivision budgetDivision = compareList.get(listPlan.get(i).fetchPrimeId());
      PlanDivision planDivision = listPlan.get(i);

      if (budgetDivision != null) {

        budgetDivision.setWorkAmountPlan(planDivision.getWorkAmount());
        budgetDivision.setSynthesisUnitpricePlan(planDivision.getCostUnitprice());
        budgetDivision.setSynthesisSumpricePlan(planDivision.getCostSumprice());
      } else {

        BudgetDivision tmp = new BudgetDivision();
        tmp.setWorkAmountActual(new BigDecimal(0));
        tmp.setSynthesisSumprice(new BigDecimal(0));
        tmp.setWorkAmount(new BigDecimal(0));
        tmp.setSource("");
        tmp.setName(planDivision.getName());
        tmp.setSort(new BigDecimal(1));
        tmp.setParentId(planDivision.getParentId());
        tmp.setOwnId(planDivision.getOwnId());
        tmp.setDivisionId(planDivision.getDivisionId());
        tmp.setCategory(planDivision.getCategory());
        tmp.setCode(planDivision.getCode());
        tmp.setHave(planDivision.getHave());
        tmp.setUnit(planDivision.getUnit());
        tmp.setTag(planDivision.getTag());
        tmp.setWorkAmountPlan(planDivision.getWorkAmount());
        tmp.setSynthesisUnitpricePlan(planDivision.getCostUnitprice());
        tmp.setSynthesisSumpricePlan(planDivision.getCostSumprice());

        listBudgetCpy.add(tmp);

        compareList.put(planDivision.getDivisionId(), tmp);
      }
    }
    // 实际
    userPage = (Page) actualService.page(page, WrapperOpt.parseWrapperOption(wrapperOpt2));
    List<ActualDivision> listActual = userPage.getRecords();
    for (int i = 0; i < listActual.size(); i++) {
      BudgetDivision budgetDivision = compareList.get(listActual.get(i).fetchPrimeId());
      ActualDivision actualDivision = listActual.get(i);
      if (budgetDivision != null) {
        budgetDivision.setWorkAmountPlan(actualDivision.getWorkAmount());
        budgetDivision.setSynthesisUnitpricePlan(actualDivision.getCostUnitprice());
        budgetDivision.setSynthesisSumpricePlan(actualDivision.getCostSumprice());
      } else {
        BudgetDivision tmp = new BudgetDivision();
        tmp.setWorkAmountActual(new BigDecimal(0));
        tmp.setSynthesisSumprice(new BigDecimal(0));
        tmp.setWorkAmount(new BigDecimal(0));
        tmp.setSource("");
        tmp.setName(actualDivision.getName());
        tmp.setSort(new BigDecimal(1));
        tmp.setParentId(actualDivision.getParentId());
        tmp.setOwnId(actualDivision.getOwnId());
        tmp.setDivisionId(actualDivision.getDivisionId());
        tmp.setCategory(actualDivision.getCategory());
        tmp.setCode(actualDivision.getCode());
        tmp.setHave(actualDivision.getHave());
        tmp.setUnit(actualDivision.getUnit());
        tmp.setTag(actualDivision.getTag());
        tmp.setWorkAmountPlan(actualDivision.getWorkAmount());
        tmp.setSynthesisUnitpricePlan(actualDivision.getCostUnitprice());
        tmp.setSynthesisSumpricePlan(actualDivision.getCostSumprice());

        listBudgetCpy.add(tmp);
        // compareList.put(actualDivision.getDivisionId(), tmp);
      }
    }
    // 放入大数组
    prelist.addAll(listBudgetCpy);
    List<BudgetDivision> resist = new ArrayList<BudgetDivision>();
    treeServiceConvert.convertProject2(project, resist);

    // 做树形渲染

    ITreeService.<BudgetDivision>treeLoop2(resist.get(0), prelist);

    PageData pageData = new PageData();
    pageData.setItemTotal(userPage.getTotal());
    pageData.setPageSize(userPage.getSize());
    pageData.setList(resist);
    return pageData;
  }
  //
  //  static <T extends Base & ISumReportService> T treeProjectSumCompare(T d0, List<T> wflist) {
  //
  //    BigDecimal workAmount = d0.fetchWorkAmount();
  //    BigDecimal synthesisUnitprice = d0.fetchSynthesisUnitprice();
  //    BigDecimal synthesisSumprice = d0.fetchSynthesisSumprice();
  //    List list = new ArrayList<T>();
  //    for (int i = 0; i < wflist.size(); i++) {
  //      T value = wflist.get(i);
  //      if (value.fetchParentId().equals(d0.fetchPrimeId())) {
  //        wflist.remove(i);
  //
  //        T tmp = ITreeService.treeProjectSumCompare(value, wflist);
  //        workAmount = workAmount.add(tmp.fetchWorkAmount());
  //        synthesisUnitprice =
  //            synthesisUnitprice.add(
  //                tmp.fetchSynthesisUnitprice() == null
  //                    ? new BigDecimal(0)
  //                    : tmp.fetchSynthesisUnitprice());
  //        synthesisSumprice =
  //            synthesisSumprice.add(
  //                tmp.fetchSynthesisSumprice() == null
  //                    ? new BigDecimal(0)
  //                    : tmp.fetchSynthesisSumprice());
  //        list.add(tmp);
  //        i = -1;
  //      }
  //    }
  //    d0.pushWorkAmount(workAmount);
  //    d0.pushSynthesisUnitprice(synthesisUnitprice);
  //    d0.pushSynthesisSumprice(synthesisSumprice);
  //    d0.setChildren(list);
  //
  //    return d0;
  //  }

  static <T extends Base & ISumReportService & ISumReportCompareService> T treeLoop2(
      T d0, List<T> wflist) {

    BigDecimal workAmount = d0.fetchWorkAmount();
    BigDecimal synthesisUnitprice = d0.fetchSynthesisUnitprice();
    BigDecimal synthesisSumprice = d0.fetchSynthesisSumprice();
    // plan
    BigDecimal workAmountPlan = d0.fetchWorkAmountPlan();
    BigDecimal synthesisUnitpricePlan = d0.fetchSynthesisUnitpricePlan();
    BigDecimal synthesisSumpricePlan = d0.fetchSynthesisSumpricePlan();
    // actual
    BigDecimal workAmountActual = d0.fetchWorkAmountActual();
    BigDecimal synthesisUnitpriceActual = d0.fetchSynthesisUnitpriceActual();
    BigDecimal synthesisSumpriceActual = d0.fetchSynthesisSumpriceActual();
    List list = new ArrayList<T>();
    for (int i = 0; i < wflist.size(); i++) {
      T value = wflist.get(i);
      if (value.fetchParentId().equals(d0.fetchPrimeId())) {
        wflist.remove(i);

        T tmp = ITreeService.treeLoop2(value, wflist);
        workAmount = workAmount.add(tmp.fetchWorkAmount());
        synthesisUnitprice =
            synthesisUnitprice.add(
                tmp.fetchSynthesisUnitprice() == null
                    ? new BigDecimal(0)
                    : tmp.fetchSynthesisUnitprice());
        synthesisSumprice =
            synthesisSumprice.add(
                tmp.fetchSynthesisSumprice() == null
                    ? new BigDecimal(0)
                    : tmp.fetchSynthesisSumprice());
        // plan
        workAmountPlan = workAmountPlan.add(tmp.fetchWorkAmountPlan());
        synthesisUnitpricePlan =
            synthesisUnitpricePlan.add(
                tmp.fetchSynthesisUnitpricePlan() == null
                    ? new BigDecimal(0)
                    : tmp.fetchSynthesisUnitpricePlan());
        synthesisSumpricePlan =
            synthesisSumpricePlan.add(
                tmp.fetchSynthesisSumpricePlan() == null
                    ? new BigDecimal(0)
                    : tmp.fetchSynthesisSumpricePlan());
        // actual
        workAmountActual = workAmountActual.add(tmp.fetchWorkAmountActual());
        synthesisUnitpriceActual =
            synthesisUnitpriceActual.add(
                tmp.fetchSynthesisUnitpriceActual() == null
                    ? new BigDecimal(0)
                    : tmp.fetchSynthesisUnitpriceActual());
        synthesisSumpriceActual =
            synthesisSumpriceActual.add(
                tmp.fetchSynthesisSumpriceActual() == null
                    ? new BigDecimal(0)
                    : tmp.fetchSynthesisSumpriceActual());
        list.add(tmp);
        i = -1;
      }
    }
    d0.pushWorkAmount(workAmount);
    d0.pushSynthesisUnitprice(synthesisUnitprice);
    d0.pushSynthesisSumprice(synthesisSumprice);
    // plan
    d0.pushWorkAmountPlan(workAmountPlan);
    d0.pushSynthesisUnitpricePlan(synthesisUnitpricePlan);
    d0.pushSynthesisSumpricePlan(synthesisSumpricePlan);
    // actual
    d0.pushWorkAmountActual(workAmountActual);
    d0.pushSynthesisUnitpriceActual(synthesisUnitpriceActual);
    d0.pushSynthesisSumpriceActual(synthesisSumpriceActual);
    d0.setChildren(list);

    return d0;
  }

  public static Base treeLoop0(Base d0, List<Base> wflist) {

    List list = new ArrayList<Base>();

    for (int i = 0; i < wflist.size(); i++) {
      Base value = wflist.get(i);
      if (value.fetchParentId().equals(d0.fetchPrimeId())) {

        wflist.remove(i);
        list.add(ITreeService.treeLoop0(value, wflist));
        i = -1;
      }
    }
    d0.setChildren(list);
    return d0;
  }

  public static void treeLoop0(Base d0, List<Base> wflist, List<Base> resList) {

    List list = new ArrayList<Base>();

    for (int i = 0; i < wflist.size(); i++) {
      Base value = wflist.get(i);
      if (value.fetchParentId().equals(d0.fetchPrimeId())) {
        wflist.remove(i);
        list.add(value);
        ITreeService.treeLoop0(value, wflist, resList);
        i = -1;
      }
    }
    resList.addAll(list);
  }

  public static <T extends Base & ISumReportService> T treeLoop1(T d0, List<T> wflist) {

    BigDecimal workAmount = d0.fetchWorkAmount();
    BigDecimal synthesisUnitprice = d0.fetchSynthesisUnitprice();
    BigDecimal synthesisSumprice = d0.fetchSynthesisSumprice();
    List list = new ArrayList<T>();
    for (int i = 0; i < wflist.size(); i++) {
      T value = wflist.get(i);
      if (value.fetchParentId().equals(d0.fetchPrimeId())) {
        wflist.remove(i);

        T tmp = ITreeService.treeLoop1(value, wflist);
        workAmount = workAmount.add(tmp.fetchWorkAmount());
        synthesisUnitprice = synthesisUnitprice.add(tmp.fetchSynthesisUnitprice());
        synthesisSumprice = synthesisSumprice.add(tmp.fetchSynthesisSumprice());
        list.add(tmp);
        ;
        i = -1;
      }
    }
    d0.pushWorkAmount(workAmount);
    d0.pushSynthesisUnitprice(synthesisUnitprice);
    d0.pushSynthesisSumprice(synthesisSumprice);
    d0.setChildren(list);

    return d0;
  }
}
