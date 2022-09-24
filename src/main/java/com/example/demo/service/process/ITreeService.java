package com.example.demo.service.process;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Base;
import com.example.demo.entity.BaseReport;
import com.example.demo.entity.Project;
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

  public static <
          T0 extends BaseReport & ISumReportService,
          T1 extends BaseReport & ISumReportService,
          T2 extends BaseReport & ISumReportService>
      PageData getTreeWithCompare(
          String selectId,
          String ownId,
          IMyService t0Service,
          IMyService t1Service,
          IMyService t2Service,
          IProjectService projectService,
          ITreeServiceConvert treeServiceConvert,
          ITreeEntityNew treeEntityNew,
          ITreeEntityNew treeEntityNew2) {

    ArrayList<T0> prelist = new ArrayList<T0>();
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

    // 组织条件
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
    // 第一个
    userPage = (Page) t0Service.page(page, WrapperOpt.parseWrapperOption(wrapperOpt2));
    List<T0> listBudget = userPage.getRecords();
    Map<Object, T0> compareList = new HashMap<Object, T0>();

    ArrayList<T0> listBudgetCpy = new ArrayList<T0>();

    for (int i = 0; i < listBudget.size(); i++) {
      listBudgetCpy.add(listBudget.get(i));
      compareList.put(listBudget.get(i).fetchPrimeId(), listBudget.get(i));
    }
    // 第二个
    userPage = (Page) t1Service.page(page, WrapperOpt.parseWrapperOption(wrapperOpt2));
    List<T1> listPlan = userPage.getRecords();

    for (int i = 0; i < listPlan.size(); i++) {
      T0 t0Division = compareList.get(listPlan.get(i).fetchPrimeId());
      T1 planDivision = listPlan.get(i);

      if (t0Division != null) {

        t0Division.pushWorkAmount2(planDivision.fetchWorkAmount());
        t0Division.pushSynthesisUnitprice2(planDivision.fetchSynthesisUnitprice());
        t0Division.pushSynthesisSumprice2(planDivision.fetchSynthesisSumprice());
      } else {

        T0 tmp = (T0) treeEntityNew.New(planDivision);

        listBudgetCpy.add(tmp);

        // compareList.put(planDivision.getDivisionId(), tmp);
      }
    }
    // 第三个
    if (t2Service != null) {
      userPage = (Page) t2Service.page(page, WrapperOpt.parseWrapperOption(wrapperOpt2));
      List<T2> listActual = userPage.getRecords();
      for (int i = 0; i < listActual.size(); i++) {
        T0 t0Division = compareList.get(listActual.get(i).fetchPrimeId());
        T2 actualDivision = listActual.get(i);
        if (t0Division != null) {
          t0Division.pushWorkAmount2(actualDivision.fetchWorkAmount());
          t0Division.pushSynthesisUnitprice2(actualDivision.fetchSynthesisUnitprice());
          t0Division.pushSynthesisSumprice2(actualDivision.fetchSynthesisSumprice());
        } else {
          T0 tmp = (T0) treeEntityNew2.New(actualDivision);

          listBudgetCpy.add(tmp);
          // compareList.put(actualDivision.getDivisionId(), tmp);
        }
      }
    }
    // 放入大数组
    prelist.addAll(listBudgetCpy);

    List<T0> resist = new ArrayList<T0>();
    treeServiceConvert.convertProject2(project, resist);

    // 做树形渲染

    ITreeService.<T0>treeLoop2(resist.get(0), prelist);

    PageData pageData = new PageData();
    pageData.setItemTotal(userPage.getTotal());
    pageData.setPageSize(userPage.getSize());
    pageData.setList(resist);
    return pageData;
  }

  static <T extends Base & ISumReportService & ISumReportCompareService> T treeLoop2(
      T d0, List<T> wflist) {

    BigDecimal workAmount = d0.fetchWorkAmount();
    BigDecimal synthesisUnitprice = d0.fetchSynthesisUnitprice();
    BigDecimal synthesisSumprice = d0.fetchSynthesisSumprice();
    // plan
    BigDecimal workAmount2 = d0.fetchWorkAmount2();
    BigDecimal synthesisUnitprice2 = d0.fetchSynthesisUnitprice2();
    BigDecimal synthesisSumprice2 = d0.fetchSynthesisSumprice2();
    // actual
    BigDecimal workAmount3 = d0.fetchWorkAmount3();
    BigDecimal synthesisUnitprice3 = d0.fetchSynthesisUnitprice3();
    BigDecimal synthesisSumprice3 = d0.fetchSynthesisSumprice3();
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
        workAmount2 = workAmount2.add(tmp.fetchWorkAmount2());
        synthesisUnitprice2 =
            synthesisUnitprice2.add(
                tmp.fetchSynthesisUnitprice2() == null
                    ? new BigDecimal(0)
                    : tmp.fetchSynthesisUnitprice2());
        synthesisSumprice2 =
            synthesisSumprice2.add(
                tmp.fetchSynthesisSumprice2() == null
                    ? new BigDecimal(0)
                    : tmp.fetchSynthesisSumprice2());
        // actual
        workAmount3 = workAmount3.add(tmp.fetchWorkAmount3());
        synthesisUnitprice3 =
            synthesisUnitprice3.add(
                tmp.fetchSynthesisUnitprice3() == null
                    ? new BigDecimal(0)
                    : tmp.fetchSynthesisUnitprice3());
        synthesisSumprice3 =
            synthesisSumprice3.add(
                tmp.fetchSynthesisSumprice3() == null
                    ? new BigDecimal(0)
                    : tmp.fetchSynthesisSumprice3());
        list.add(tmp);
        i = -1;
      }
    }
    d0.pushWorkAmount(workAmount);
    d0.pushSynthesisUnitprice(synthesisUnitprice);
    d0.pushSynthesisSumprice(synthesisSumprice);
    // plan
    d0.pushWorkAmount2(workAmount2);
    d0.pushSynthesisUnitprice2(synthesisUnitprice2);
    d0.pushSynthesisSumprice2(synthesisSumprice2);
    // actual
    d0.pushWorkAmount3(workAmount3);
    d0.pushSynthesisUnitprice3(synthesisUnitprice3);
    d0.pushSynthesisSumprice3(synthesisSumprice3);
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
