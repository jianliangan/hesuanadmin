package com.example.demo.service.process;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Base;
import com.example.demo.entity.BaseMachine;
import com.example.demo.entity.BaseReport;
import com.example.demo.entity.Project;
import com.example.demo.service.IMyService;
import com.example.demo.service.IProjectService;
import com.example.demo.service.common.ISumReportCompareService;
import com.example.demo.service.common.ISumReportService;
import com.example.demo.service.common.PageData;
import com.example.demo.service.common.SearchStr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface ITreeService {
    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ITreeService.class);

    public static <T extends BaseReport & ISumReportService> PageData getTreeWithPrice(
            String projectId,
            String ownId,
            IMyService myService,
            IProjectService projectService,
            ITreeServiceConvert treeServiceConvert, SearchStr searchStr) {

        ArrayList<T> prelist = new ArrayList<T>();
        ArrayList<Base> relist = new ArrayList<Base>();
        // 把所有需要显示的项目相关id放入数组
        List instr = new ArrayList<String>();
        int pageIndex = 1;
        // 找到选中的，作为最大的那个节点
        Project project = projectService.getById(projectId);
        // 放入大树

        instr.add(project.getProjectId());
        // 从项目节点中找到选中节点下面的所有子节点

        Page page = new Page(pageIndex, 1000);


        Map wheres = new HashMap<String, String>();
        wheres.put("own_id", ownId);

        QueryWrapper wrapper = new QueryWrapper();
        List orderColumn = new ArrayList<>();
        orderColumn.add("sort");
        wrapper.orderBy(true, true, orderColumn);
        wrapper.allEq(wheres);

        //Page userPage = projectService.page(page, wrapper);
        List<Base> list = projectService.list(wrapper);
        // 树扁平化
        List<Base> tmplist = new ArrayList<Base>();
        for (int i = 0; i < list.size(); i++) {
            Base value = list.get(i);
            if (value.fetchParentId().toString().equals(projectId)) {
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


        QueryWrapper wrapper2 = new QueryWrapper();
        List orderColumn2 = new ArrayList<>();
        orderColumn2.add("sort");
        wrapper2.orderBy(true, true, orderColumn2);

        if (instr.size() > 0) {

            wrapper2.in("own_id", instr.toArray());
        }


        //userPage = (Page) myService.page(page, wrapper2);
        List<T> list2 = myService.list(wrapper2);//userPage.getRecords();
//前6个字段查询过滤
        filterListSearch(list2, searchStr);

        // 放入大数组
        prelist.addAll(list2);
        List<T> resist = new ArrayList<T>();
        treeServiceConvert.convertProject2(project, resist);
        // 做树形渲染
        treeLoop1((T) resist.get(0), prelist);
        PageData pageData = new PageData();
        pageData.setItemTotal(1);
        pageData.setPageSize(1);
        pageData.setList(resist);

        return pageData;
    }

    static <T extends BaseReport & ISumReportService> List<T> filterByMachine(
            String subPackageId, String cmd, IMyService machineService, List<T> listDivision) {

        Page page = new Page(1, 1000);


        QueryWrapper wrapper = new QueryWrapper();
        Map wheres = new HashMap<String, String>();
        wheres.put(cmd, subPackageId);
        wrapper.allEq(wheres);

        //Page userPage = (Page) machineService.page(page, wrapper);
        List<BaseMachine> list3 = machineService.list(wrapper);//userPage.getRecords();
        System.out.println("sssssssssss");
        System.out.println(list3);
        Map<Object, BigDecimal[]> list3Map = new HashMap<Object, BigDecimal[]>();
        for (int i = 0; i < list3.size(); i++) {
            Object key = list3.get(i).fetchParentId();
            BigDecimal[] value = list3Map.get(key);
            if (value == null) {
                list3Map.put(
                        key,
                        new BigDecimal[]{
                                list3.get(i).getCount(), list3.get(i).getPrice(), list3.get(i).getCombinedPrice()
                        });
            } else {
                value[0] = value[0].add(list3.get(i).getCount());
                value[1] = value[1].add(list3.get(i).getPrice());
                value[2] = value[2].add(list3.get(i).getCombinedPrice());
            }
        }
        // 除去本分包商的内容
        for (int i = 0; i < listDivision.size(); i++) {
            Object key = listDivision.get(i).fetchPrimeId();
            if (listDivision.get(i).getCode() == null || listDivision.get(i).getCode().length() == 0) {
                continue;
            } else {
                BigDecimal[] tmp = list3Map.get(key);
                if (tmp != null) {

                    listDivision.get(i).pushWorkAmount(tmp[0]);
                    listDivision.get(i).pushCostUnitprice(tmp[1]);
                    listDivision.get(i).pushCostSumprice(tmp[2]);

                } else {
                    listDivision.remove(i);
                    i--;
                }
            }
        }
        return listDivision;
    }

    /**
     * 固定的只支持实际和计划
     */
    public static <
            T1 extends BaseReport & ISumReportService,
            T2 extends BaseReport & ISumReportService,
            T3 extends BaseReport & ISumReportService>
    PageData getTreeWithCompareMachine(
            String projectId,
            String ownId,
            IMyService t1Service,
            IMyService t2Service,
            IMyService t3Service,
            IProjectService projectService,
            ITreeServiceConvert treeServiceConvert,
            ITreeEntityNew treeEntityNew,
            ITreeEntityNew treeEntityNew2,
            String subPackageId,
            String cmd,
            IMyService machineService0,
            IMyService machineService1,
            IMyService machineService2, SearchStr searchStr) {

        ArrayList<T1> prelist = new ArrayList<T1>();
        ArrayList<Base> relist = new ArrayList<Base>();
        // 把所有需要显示的项目相关id放入数组
        List instr = new ArrayList<String>();
        int pageIndex = 1;
        // 找到选中的，作为最大的那个节点
        Project project = projectService.getById(projectId);
        // 放入大树

        instr.add(project.getProjectId());
        // 从项目节点中找到选中节点下面的所有子节点
        // System.out.println(",,,,,33000,,,,," + project);
        Page page = new Page(pageIndex, 1000);


        QueryWrapper wrapper = new QueryWrapper();

        List orderColumn = new ArrayList<>();
        orderColumn.add("sort");
        wrapper.orderBy(true, true, orderColumn);

        Map wheres = new HashMap<String, String>();
        wheres.put("own_id", ownId);
        wrapper.allEq(wheres);


        //Page userPage = projectService.page(page, wrapper);
        List<Base> list = projectService.list(wrapper);//userPage.getRecords();
        // 项目树扁平化
        List<Base> tmplist = new ArrayList<Base>();
        for (int i = 0; i < list.size(); i++) {
            Base value = list.get(i);
            if (value.fetchParentId().toString().equals(projectId)) {
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


        QueryWrapper wrapper2 = new QueryWrapper();

        List orderColumn2 = new ArrayList<>();
        orderColumn2.add("sort");
        wrapper2.orderBy(true, true, orderColumn2);
        if (instr.size() > 0) {

            wrapper2.in("own_id", instr.toArray());
        }


        // 第一个
        // userPage =;
        List<T1> listDivision = t1Service.list(wrapper2);

        // 处理第一个，去掉不含有分包商的信息
        listDivision = filterByMachine(subPackageId, cmd, machineService0, listDivision);
        /// 结束

        Map<Object, T1> compareList = new HashMap<Object, T1>();

        ArrayList<T1> listBudgetCpy = new ArrayList<T1>();

        for (int i = 0; i < listDivision.size(); i++) {
            listBudgetCpy.add((T1) listDivision.get(i));

            compareList.put(listDivision.get(i).fetchPrimeId(), (T1) listDivision.get(i));
        }
        // 第二个
        //userPage = (Page) t2Service.page(page, wrapper2);
        List<T2> listPlan = t2Service.list(wrapper2);//userPage.getRecords();
        // 处理第二个，去掉不含有分包商的信息

        listPlan = ITreeService.<T2>filterByMachine(subPackageId, cmd, machineService1, listPlan);
        /// 结束

        for (int i = 0; i < listPlan.size(); i++) {
            T1 t0Division = compareList.get(listPlan.get(i).fetchPrimeId());
            T2 planDivision = listPlan.get(i);

            if (t0Division != null) {

                t0Division.pushWorkAmount2(planDivision.fetchWorkAmount());
                t0Division.pushCostUnitprice2(planDivision.fetchCostUnitprice());
                t0Division.pushCostSumprice2(planDivision.fetchCostSumprice());


            } else {

                T1 tmp = (T1) treeEntityNew.New(planDivision);

                listBudgetCpy.add(tmp);

                // compareList.put(planDivision.getDivisionId(), tmp);
            }
        }

        // 第三个
        if (t3Service != null) {
            //userPage = (Page) t3Service.page(page, wrapper2);
            List<T3> listActual = t3Service.list(wrapper2);//userPage.getRecords();
            listActual = ITreeService.<T3>filterByMachine(subPackageId, cmd, machineService2, listActual);
            for (int i = 0; i < listActual.size(); i++) {
                T1 t0Division = compareList.get(listActual.get(i).fetchPrimeId());
                T3 actualDivision = listActual.get(i);
                if (t0Division != null) {
                    t0Division.pushWorkAmount2(actualDivision.fetchWorkAmount());
                    t0Division.pushCostUnitprice2(actualDivision.fetchCostUnitprice());
                    t0Division.pushCostSumprice2(actualDivision.fetchCostSumprice());
                } else {
                    T1 tmp = (T1) treeEntityNew2.New(actualDivision);

                    listBudgetCpy.add(tmp);
                    // compareList.put(actualDivision.getDivisionId(), tmp);
                }
            }
        }
        filterListSearch(listBudgetCpy, searchStr);
        // 放入大数组
        prelist.addAll(listBudgetCpy);

        List<T1> resist = new ArrayList<T1>();
        treeServiceConvert.convertProject2(project, resist);

        // 做树形渲染

        ITreeService.<T1>treeLoop2(resist.get(0), prelist);

        PageData pageData = new PageData();
        pageData.setItemTotal(1);
        pageData.setPageSize(1);
        pageData.setList(resist);
        return pageData;
    }

    public static <
            T1 extends BaseReport & ISumReportService,
            T2 extends BaseReport & ISumReportService,
            T3 extends BaseReport & ISumReportService>
    PageData getTreeWithCompare(
            String projectId,
            String ownId,
            IMyService t1Service,
            IMyService t2Service,
            IMyService t3Service,
            IProjectService projectService,
            ITreeServiceConvert treeServiceConvert,
            ITreeEntityNew treeEntityNew,
            ITreeEntityNew treeEntityNew2, SearchStr searchStr) {

        ArrayList<T1> prelist = new ArrayList<T1>();
        ArrayList<Base> relist = new ArrayList<Base>();
        // 把所有需要显示的项目相关id放入数组
        List instr = new ArrayList<String>();
        int pageIndex = 1;
        // 找到选中的，作为最大的那个节点
        Project project = projectService.getById(projectId);
        // 放入大树

        instr.add(project.getProjectId());
        // 从项目节点中找到选中节点下面的所有子节点
        // System.out.println(",,,,,33000,,,,," + project);
        Page page = new Page(pageIndex, 1000);


        QueryWrapper wrapper = new QueryWrapper();

        List orderColumn = new ArrayList<>();
        orderColumn.add("sort");
        wrapper.orderBy(true, true, orderColumn);
        Map wheres = new HashMap<String, String>();
        wheres.put("own_id", ownId);


        //Page userPage = projectService.page(page, wrapper);
        List<Base> list = projectService.list(wrapper);//userPage.getRecords();
        // 项目树扁平化
        List<Base> tmplist = new ArrayList<Base>();
        for (int i = 0; i < list.size(); i++) {
            Base value = list.get(i);
            if (value.fetchParentId().toString().equals(projectId)) {
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


        QueryWrapper wrapper2 = new QueryWrapper();

        List orderColumn2 = new ArrayList<>();
        orderColumn2.add("sort");
        wrapper.orderBy(true, true, orderColumn2);
        if (instr.size() > 0) {

            wrapper2.in("own_id", instr.toArray());
        }
        // 第一个
        //userPage = (Page) t1Service.page(page, wrapper2);
        List<T1> listBudget = t1Service.list(wrapper2);//userPage.getRecords();
        Map<Object, T1> compareList = new HashMap<Object, T1>();

        ArrayList<T1> listBudgetCpy = new ArrayList<T1>();

        for (int i = 0; i < listBudget.size(); i++) {
            listBudgetCpy.add(listBudget.get(i));
            compareList.put(listBudget.get(i).fetchPrimeId(), listBudget.get(i));
        }
        // 第二个
        //userPage = (Page) t2Service.page(page, wrapper2);
        List<T2> listPlan = t2Service.list(wrapper2);//userPage.getRecords();

        for (int i = 0; i < listPlan.size(); i++) {
            T1 t0Division = compareList.get(listPlan.get(i).fetchPrimeId());
            T2 planDivision = listPlan.get(i);

            if (t0Division != null) {

                t0Division.pushWorkAmount2(planDivision.fetchWorkAmount());
                t0Division.pushCostUnitprice2(planDivision.fetchCostUnitprice());
                t0Division.pushCostSumprice2(planDivision.fetchCostSumprice());
            } else {

                T1 tmp = (T1) treeEntityNew.New(planDivision);

                listBudgetCpy.add(tmp);

                // compareList.put(planDivision.getDivisionId(), tmp);
            }
        }
        // 第三个
        if (t3Service != null) {
            //userPage = (Page) t3Service.page(page, wrapper2);
            List<T3> listActual = t3Service.list(wrapper2);//userPage.getRecords();
            for (int i = 0; i < listActual.size(); i++) {
                T1 t0Division = compareList.get(listActual.get(i).fetchPrimeId());
                T3 actualDivision = listActual.get(i);
                if (t0Division != null) {
                    t0Division.pushWorkAmount2(actualDivision.fetchWorkAmount());
                    t0Division.pushCostUnitprice2(actualDivision.fetchCostUnitprice());
                    t0Division.pushCostSumprice2(actualDivision.fetchCostSumprice());
                } else {
                    T1 tmp = (T1) treeEntityNew2.New(actualDivision);

                    listBudgetCpy.add(tmp);
                    // compareList.put(actualDivision.getDivisionId(), tmp);
                }
            }
        }

        filterListSearch(listBudgetCpy, searchStr);
        // 放入大数组
        prelist.addAll(listBudgetCpy);

        List<T1> resist = new ArrayList<T1>();
        treeServiceConvert.convertProject2(project, resist);

        // 做树形渲染

        ITreeService.<T1>treeLoop2(resist.get(0), prelist);

        PageData pageData = new PageData();
        pageData.setItemTotal(1);
        pageData.setPageSize(1);
        pageData.setList(resist);
        return pageData;
    }

    static <T extends BaseReport & ISumReportService> void filterListSearch(List<T> list2, SearchStr searchStr) {
        for (int i = 0; i < list2.size(); i++) {
            T tmp = list2.get(i);
            if (tmp.getCode() != null && tmp.getCode().length() > 0) {
                boolean exclude = false;
                if (searchStr.category != null) {
                    exclude = true;
                    if (tmp.getCategory() != null && tmp.getCategory().indexOf(searchStr.category) != -1) {
                        exclude = false;
                    }
                }
                if (exclude == false && searchStr.code != null) {
                    exclude = true;
                    if (tmp.getCode() != null && tmp.getCode().indexOf(searchStr.code) != -1) {
                        exclude = false;
                    }
                }
                if (exclude == false && searchStr.distinction != null) {
                    exclude = true;
                    if (tmp.getDistinction() != null && tmp.getDistinction().indexOf(searchStr.distinction) != -1) {
                        exclude = false;
                    }
                }
                if (exclude == false && searchStr.name != null) {
                    exclude = true;
                    if (tmp.getName() != null && tmp.getName().indexOf(searchStr.name) != -1) {
                        exclude = false;
                    }
                }
                if (exclude == false && searchStr.subject != null) {
                    exclude = true;
                    if (tmp.getSubject() != null && tmp.getSubject().indexOf(searchStr.subject) != -1) {
                        exclude = false;
                    }
                }
                if (exclude) {
                    list2.remove(i);
                    i--;
                }
            }


        }
    }

    static <T extends Base & ISumReportService & ISumReportCompareService> T treeLoop2(
            T d0, List<T> wflist) {

        BigDecimal workAmount = d0.fetchWorkAmount();
        BigDecimal costUnitprice = d0.fetchCostUnitprice();
        BigDecimal costSumprice = d0.fetchCostSumprice();
        // plan
        BigDecimal workAmount2 = d0.fetchWorkAmount2();
        BigDecimal costUnitprice2 = d0.fetchCostUnitprice2();
        BigDecimal costSumprice2 = d0.fetchCostSumprice2();
        // actual
        BigDecimal workAmount3 = d0.fetchWorkAmount3();
        BigDecimal costUnitprice3 = d0.fetchCostUnitprice3();
        BigDecimal costSumprice3 = d0.fetchCostSumprice3();
        List list = new ArrayList<T>();
        for (int i = 0; i < wflist.size(); i++) {
            T value = wflist.get(i);
            if (value.fetchParentId().equals(d0.fetchPrimeId())) {
                wflist.remove(i);

                T tmp = ITreeService.treeLoop2(value, wflist);
                workAmount = workAmount.add(tmp.fetchWorkAmount());
                costUnitprice =
                        costUnitprice.add(
                                tmp.fetchCostUnitprice() == null ? new BigDecimal(0) : tmp.fetchCostUnitprice());
                costSumprice =
                        costSumprice.add(
                                tmp.fetchCostSumprice() == null ? new BigDecimal(0) : tmp.fetchCostSumprice());
                // plan
                workAmount2 = workAmount2.add(tmp.fetchWorkAmount2());
                costUnitprice2 =
                        costUnitprice2.add(
                                tmp.fetchCostUnitprice2() == null ? new BigDecimal(0) : tmp.fetchCostUnitprice2());
                costSumprice2 =
                        costSumprice2.add(
                                tmp.fetchCostSumprice2() == null ? new BigDecimal(0) : tmp.fetchCostSumprice2());
                // actual
                workAmount3 = workAmount3.add(tmp.fetchWorkAmount3());
                costUnitprice3 =
                        costUnitprice3.add(
                                tmp.fetchCostUnitprice3() == null ? new BigDecimal(0) : tmp.fetchCostUnitprice3());
                costSumprice3 =
                        costSumprice3.add(
                                tmp.fetchCostSumprice3() == null ? new BigDecimal(0) : tmp.fetchCostSumprice3());
                list.add(tmp);
                i = -1;
            }
        }
        d0.pushWorkAmount(workAmount);
        d0.pushCostUnitprice(costUnitprice);
        d0.pushCostSumprice(costSumprice);
        // plan
        d0.pushWorkAmount2(workAmount2);
        d0.pushCostUnitprice2(costUnitprice2);
        d0.pushCostSumprice2(costSumprice2);
        // actual
        d0.pushWorkAmount3(workAmount3);
        d0.pushCostUnitprice3(costUnitprice3);
        d0.pushCostSumprice3(costSumprice3);
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
        BigDecimal costUnitprice = d0.fetchCostUnitprice();
        BigDecimal costSumprice = d0.fetchCostSumprice();
        List list = new ArrayList<T>();
        for (int i = 0; i < wflist.size(); i++) {
            T value = wflist.get(i);
            if (value.fetchParentId().equals(d0.fetchPrimeId())) {
                wflist.remove(i);

                T tmp = ITreeService.treeLoop1(value, wflist);
                workAmount = workAmount.add(tmp.fetchWorkAmount());
                costUnitprice = costUnitprice.add(tmp.fetchCostUnitprice());
                costSumprice = costSumprice.add(tmp.fetchCostSumprice());
                list.add(tmp);
                ;
                i = -1;
            }
        }
        d0.pushWorkAmount(workAmount);
        d0.pushCostUnitprice(costUnitprice);
        d0.pushCostSumprice(costSumprice);
        d0.setChildren(list);

        return d0;
    }
}
