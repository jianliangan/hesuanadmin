package com.example.demo.service.impl.budget;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.controller.common.DataDivision;
import com.example.demo.controller.common.DataMeasure;
import com.example.demo.controller.common.DataOther;
import com.example.demo.entity.ListInventory;
import com.example.demo.entity.LockTable;
import com.example.demo.entity.ProjectIndex;
import com.example.demo.entity.actual.ActualDivision;
import com.example.demo.entity.actual.ActualMeasure;
import com.example.demo.entity.budget.BudgetDivision;
import com.example.demo.entity.budget.BudgetMeasure;
import com.example.demo.entity.budget.BudgetOther;
import com.example.demo.entity.plan.PlanDivision;
import com.example.demo.entity.plan.PlanMeasure;
import com.example.demo.mapper.ListInventoryMapper;
import com.example.demo.mapper.LockTableMapper;
import com.example.demo.mapper.actual.ActualDivisionMapper;
import com.example.demo.mapper.actual.ActualMeasureMapper;
import com.example.demo.mapper.actual.machine.ActualDivisionMachineMapper;
import com.example.demo.mapper.actual.machine.ActualMeasureMachineMapper;
import com.example.demo.mapper.budget.BudgetDivisionMapper;
import com.example.demo.mapper.budget.BudgetMeasureMapper;
import com.example.demo.mapper.budget.machine.BudgetDivisionMachineMapper;
import com.example.demo.mapper.budget.machine.BudgetMeasureMachineMapper;
import com.example.demo.mapper.plan.PlanDivisionMapper;
import com.example.demo.mapper.plan.PlanMeasureMapper;
import com.example.demo.mapper.plan.machine.PlanDivisionMachineMapper;
import com.example.demo.mapper.plan.machine.PlanMeasureMachineMapper;
import com.example.demo.service.budget.IBudgetDivisionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.controller.common.BaseController.getSnowFlake;

@Slf4j
@Service
@Validated
public class BudgetDivisionServiceImpl extends ServiceImpl<BudgetDivisionMapper, BudgetDivision>
        implements IBudgetDivisionService {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BudgetMeasureMapper budgetMeasureMapper;
    @Autowired
    private PlanDivisionMapper planDivisionMapper;
    @Autowired
    private PlanMeasureMapper planMeasureMapper;
    @Autowired
    private ActualDivisionMapper actualDivisionMapper;
    @Autowired
    private ActualMeasureMapper actualMeasureMapper;

    @Autowired
    private BudgetMeasureMachineMapper budgetMeasureMachineMapper;
    @Autowired
    private BudgetDivisionMachineMapper budgetDivisionMachineMapper;
    @Autowired
    private PlanDivisionMachineMapper planDivisionMachineMapper;
    @Autowired
    private PlanMeasureMachineMapper planMeasureMachineMapper;
    @Autowired
    private ActualDivisionMachineMapper actualDivisionMachineMapper;
    @Autowired
    private ActualMeasureMachineMapper actualMeasureMachineMapper;

    @Autowired
    ListInventoryMapper listInventoryMapper;

    @Autowired
    LockTableMapper lockTableMapper;

    public BudgetDivision getPrimeOne(Object key) {
        QueryWrapper wrapper = new QueryWrapper();
        Map wheres = new HashMap<String, String>();
        wheres.put("division_id", key);
        wrapper.allEq(wheres);
        return super.getOne(wrapper);
    }


    @Override
    public String checkCanDelete(BudgetDivision instan) {
        QueryWrapper wrapper = new QueryWrapper<ProjectIndex>();
        wrapper.eq("parent_id", instan.fetchPrimeId());
        wrapper.last("limit 1");
        BudgetDivision child = getOne(wrapper);

        if (child != null) {
            return "删除错误：可能是存在子项，或者存在数据";
        }
        return null;
    }

    private void extendInventory(BudgetDivision entity) {
        ListInventory listInventory = new ListInventory();
        listInventory.setTableId(entity.getDivisionId());
        listInventory.setTableType("yusuan");
        listInventoryMapper.insert(listInventory);
    }

    @Override
    public boolean updateById(BudgetDivision entity) {
        extendInventory(entity);
        return IBudgetDivisionService.super.updateById(entity);
    }

    @Override
    public boolean save(BudgetDivision entity) {
        extendInventory(entity);
        return IBudgetDivisionService.super.save(entity);
    }


    @Transactional
    public void ImportData(String projectId, String filePath) {
        QueryWrapper wrapper = new QueryWrapper();
        Map wheres = new HashMap<String, String>();
        wheres.put("id", "budgetall");
        wrapper.allEq(wheres);
        wrapper.last("for update");
        LockTable lockTable = lockTableMapper.selectOne(wrapper);
        final Long[] sortBudget = {lockTable.getBudget()};

        BudgetDivisionMapper that2 = this.getBaseMapper();
        try (ExcelReader excelReader = EasyExcel.read(filePath).build()) {
            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            ReadSheet readSheet1 =
                    EasyExcel.readSheet(1)
                            .head(DataDivision.class)
                            .registerReadListener(
                                    new ReadListener<DataDivision>() {
                                        /** 单次缓存的数据量 */
                                        public static final int BATCH_COUNT = 100;

                                        public int start = -1;

                                        /** 临时存储 */
                                        private List<DataDivision> cachedDataList =
                                                ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

                                        @Override
                                        public void invoke(DataDivision data, AnalysisContext context) {

                                            // if (data.getSort().substring(0, data.getSort().length() - 15) == "工程名称：")

                                            if (data.getSort() != null) {
                                                if (data.getSort().length() > 5) {
                                                    if (data.getSort().substring(0, 5).equals("工程名称：")) {
                                                        start = 0;
                                                    }
                                                } else if (data.getSort().length() > 0) {
                                                    if (data.getSort().substring(0, 1).equals("/")) {
                                                        start = -1;
                                                    }
                                                }
                                            }
                                            if (start != -1) {
                                                start++;
                                            } else {
                                                return;
                                            }
                                            if (start > 3) { // 因为从1开始的
                                                cachedDataList.add(data);

                                                if (cachedDataList.size() >= BATCH_COUNT) {
                                                    saveData();
                                                    // 存储完成清理 list
                                                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                                                }
                                            }
                                        }

                                        @Override
                                        public void doAfterAllAnalysed(AnalysisContext context) {
                                            saveData();
                                        }

                                        /** 加上存储数据库 */
                                        private void saveData() {
                                            String parentIdBudget = "";
                                            String parentIdPlan = "";
                                            String parentIdActual = "";
                                            for (int i = 0; i < cachedDataList.size(); i++) {
                                                DataDivision mydata = cachedDataList.get(i);
                                                BudgetDivision mydata2 = new BudgetDivision();
                                                PlanDivision mydata2plan = new PlanDivision();
                                                ActualDivision mydata2actual = new ActualDivision();

                                                mydata2.setName(mydata.getName());
                                                mydata2plan.setName(mydata.getName());
                                                mydata2actual.setName(mydata.getName());

                                                mydata2.setDistinction(mydata.getDistinction());
                                                mydata2plan.setDistinction(mydata.getDistinction());
                                                mydata2actual.setDistinction(mydata.getDistinction());

                                                mydata2.setUnit(mydata.getUnit());
                                                mydata2plan.setUnit(mydata.getUnit());
                                                mydata2actual.setUnit(mydata.getUnit());

                                                if (NumberUtils.isCreatable(mydata.getWorkAmount())) {
                                                    BigDecimal tmp = new BigDecimal(mydata.getWorkAmount());
                                                    if (mydata.getCode() == null || mydata.getCode().length() == 0) {
                                                        tmp = new BigDecimal(0);
                                                    }
                                                    mydata2.setWorkAmount(tmp);
                                                    mydata2plan.setWorkAmount(tmp);
                                                    mydata2actual.setWorkAmount(tmp);
                                                }
                                                if (NumberUtils.isCreatable(mydata.getCostUnitprice())) {
                                                    BigDecimal tmp = new BigDecimal(mydata.getCostUnitprice());
                                                    if (mydata.getCode() == null || mydata.getCode().length() == 0) {
                                                        tmp = new BigDecimal(0);
                                                    }
                                                    mydata2.setCostUnitprice(tmp);
                                                    mydata2plan.setCostUnitprice(tmp);
                                                    mydata2actual.setCostUnitprice(tmp);
                                                }

                                                if (NumberUtils.isCreatable(mydata.getCostSumprice())) {
                                                    BigDecimal tmp = new BigDecimal(mydata.getCostSumprice());
                                                    if (mydata.getCode() == null || mydata.getCode().length() == 0) {
                                                        tmp = new BigDecimal(0);
                                                    }
                                                    mydata2.setCostSumprice(tmp);
                                                    mydata2plan.setCostSumprice(tmp);
                                                    mydata2actual.setCostSumprice(tmp);
                                                }
                                                boolean isParent = false;
                                                if (mydata.getCode() != null && mydata.getCode().length() > 0) {
                                                    String tmp = getSnowFlake().nextId() + "";
                                                    mydata2.setCode(mydata.getCode());
                                                    mydata2.setDivisionId(tmp);
                                                    mydata2.setParentId(parentIdBudget);

                                                    mydata2plan.setCode(mydata.getCode());
                                                    mydata2plan.setDivisionId(tmp);
                                                    mydata2plan.setParentId(parentIdPlan);

                                                    mydata2actual.setCode(mydata.getCode());
                                                    mydata2actual.setDivisionId(tmp);
                                                    mydata2actual.setParentId(parentIdActual);
                                                    isParent = false;

                                                } else {
                                                    parentIdBudget = parentIdPlan = parentIdActual = getSnowFlake().nextId() + "";
                                                    mydata2.setDivisionId(parentIdBudget);
                                                    mydata2.setParentId(projectId);

                                                    mydata2plan.setDivisionId(parentIdBudget);
                                                    mydata2plan.setParentId(projectId);

                                                    mydata2actual.setDivisionId(parentIdBudget);
                                                    mydata2actual.setParentId(projectId);
                                                    isParent = true;
                                                }

                                                BigDecimal sortBudgetTmp = new BigDecimal(++sortBudget[0]);
                                                BigDecimal sortPlanTmp = new BigDecimal(++sortBudget[0]);
                                                BigDecimal sortActualTmp = new BigDecimal(++sortBudget[0]);
                                                mydata2.setOwnId(projectId);
                                                mydata2.setSort(sortBudgetTmp);

                                                mydata2plan.setOwnId(projectId);
                                                mydata2plan.setSort(sortPlanTmp);

                                                mydata2actual.setOwnId(projectId);
                                                mydata2actual.setSort(sortActualTmp);
                                                String tmpidBudget = "";
                                                String tmpidPlan = "";
                                                String tmpidActual = "";

                                                tmpidBudget = save2update(mydata2, that2, budgetDivisionMachineMapper, "budget");
                                                tmpidPlan = save2update(mydata2plan, planDivisionMapper, planDivisionMachineMapper, "plan");
                                                tmpidActual = save2update(mydata2actual, actualDivisionMapper, actualDivisionMachineMapper, "actual");
                                                if (isParent) {
                                                    parentIdBudget = tmpidBudget;
                                                    parentIdPlan = tmpidPlan;
                                                    parentIdActual = tmpidActual;
                                                }
                                            }
                                        }
                                    })
                            .build();
            ReadSheet readSheet2 =
                    EasyExcel.readSheet(2)
                            .head(DataMeasure.class)
                            .registerReadListener(
                                    new ReadListener<DataMeasure>() {
                                        /** 单次缓存的数据量 */
                                        public static final int BATCH_COUNT = 100;

                                        public int start = -1;
                                        /** 临时存储 */
                                        private List<DataMeasure> cachedDataList =
                                                ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

                                        @Override
                                        public void invoke(DataMeasure data, AnalysisContext context) {

                                            if (data.getSort() != null) {
                                                if (data.getSort().length() > 5) {
                                                    if (data.getSort().substring(0, 5).equals("工程名称：")) {
                                                        start = 0;
                                                    }
                                                } else if (data.getSort().length() > 0) {
                                                    if (data.getSort().substring(0, 1).equals("/")) {
                                                        start = -1;
                                                    }
                                                }
                                            }
                                            if (start != -1) {
                                                start++;
                                            } else {
                                                return;
                                            }
                                            if (start > 3) { // 因为从1开始的
                                                cachedDataList.add(data);
                                                log.info("{}", data);
                                                if (cachedDataList.size() >= BATCH_COUNT) {
                                                    saveData();
                                                    // 存储完成清理 list
                                                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                                                }
                                            }
                                        }

                                        @Override
                                        public void doAfterAllAnalysed(AnalysisContext context) {
                                            saveData();
                                        }

                                        /** 加上存储数据库 */
                                        private void saveData() {
                                            String parentIdBudget = "";
                                            String parentIdPlan = "";
                                            String parentIdActual = "";
                                            for (int i = 0; i < cachedDataList.size(); i++) {
                                                DataMeasure mydata = cachedDataList.get(i);
                                                BudgetMeasure mydata2 = new BudgetMeasure();

                                                PlanMeasure mydata2plan = new PlanMeasure();
                                                ActualMeasure mydata2actual = new ActualMeasure();

                                                mydata2.setName(mydata.getName());
                                                mydata2plan.setName(mydata.getName());
                                                mydata2actual.setName(mydata.getName());

                                                mydata2.setDistinction(mydata.getDistinction());
                                                mydata2plan.setDistinction(mydata.getDistinction());
                                                mydata2actual.setDistinction(mydata.getDistinction());

                                                mydata2.setUnit(mydata.getUnit());
                                                mydata2plan.setUnit(mydata.getUnit());
                                                mydata2actual.setUnit(mydata.getUnit());

                                                if (NumberUtils.isCreatable(mydata.getWorkAmount())) {
                                                    BigDecimal tmp = new BigDecimal(mydata.getWorkAmount());
                                                    if (mydata.getCode() == null || mydata.getCode().length() == 0) {
                                                        tmp = new BigDecimal(0);
                                                    }
                                                    mydata2.setWorkAmount(tmp);
                                                    mydata2plan.setWorkAmount(tmp);
                                                    mydata2actual.setWorkAmount(tmp);
                                                }
                                                if (NumberUtils.isCreatable(mydata.getCostUnitprice())) {
                                                    BigDecimal tmp = new BigDecimal(mydata.getCostUnitprice());
                                                    if (mydata.getCode() == null || mydata.getCode().length() == 0) {
                                                        tmp = new BigDecimal(0);
                                                    }
                                                    mydata2.setCostUnitprice(tmp);
                                                    mydata2plan.setCostUnitprice(tmp);
                                                    mydata2actual.setCostUnitprice(tmp);
                                                }
                                                if (NumberUtils.isCreatable(mydata.getCostSumprice())) {
                                                    BigDecimal tmp = new BigDecimal(mydata.getCostSumprice());
                                                    if (mydata.getCode() == null || mydata.getCode().length() == 0) {
                                                        tmp = new BigDecimal(0);
                                                    }
                                                    mydata2.setCostSumprice(tmp);
                                                    mydata2plan.setCostSumprice(tmp);
                                                    mydata2actual.setCostSumprice(tmp);
                                                }
                                                boolean isParent = false;
                                                if (mydata.getCode() != null && mydata.getCode().length() > 0) {
                                                    String tmp = getSnowFlake().nextId() + "";

                                                    mydata2.setCode(mydata.getCode());
                                                    mydata2.setMeasureId(tmp);
                                                    mydata2.setParentId(parentIdBudget);

                                                    mydata2plan.setCode(mydata.getCode());
                                                    mydata2plan.setMeasureId(tmp);
                                                    mydata2plan.setParentId(parentIdPlan);

                                                    mydata2actual.setCode(mydata.getCode());
                                                    mydata2actual.setMeasureId(tmp);
                                                    mydata2actual.setParentId(parentIdActual);
                                                    isParent = false;
                                                } else {
                                                    parentIdBudget = parentIdPlan = parentIdActual = getSnowFlake().nextId() + "";
                                                    mydata2.setMeasureId(parentIdBudget);
                                                    mydata2.setParentId(projectId);

                                                    mydata2plan.setMeasureId(parentIdBudget);
                                                    mydata2plan.setParentId(projectId);

                                                    mydata2actual.setMeasureId(parentIdBudget);
                                                    mydata2actual.setParentId(projectId);
                                                    isParent = true;
                                                }

                                                BigDecimal sortBudgetTmp = new BigDecimal(++sortBudget[0]);
                                                BigDecimal sortPlanTmp = new BigDecimal(++sortBudget[0]);
                                                BigDecimal sortActualTmp = new BigDecimal(++sortBudget[0]);

                                                mydata2.setOwnId(projectId);
                                                mydata2.setSort(sortBudgetTmp);

                                                mydata2plan.setOwnId(projectId);
                                                mydata2plan.setSort(sortPlanTmp);

                                                mydata2actual.setOwnId(projectId);
                                                mydata2actual.setSort(sortActualTmp);
                                                String tmpidBudget = "";
                                                String tmpidPlan = "";
                                                String tmpidActual = "";
                                                tmpidBudget = save2update(mydata2, budgetMeasureMapper, budgetMeasureMachineMapper, "budget");
                                                tmpidPlan = save2update(mydata2plan, planMeasureMapper, planMeasureMachineMapper, "plan");
                                                tmpidActual = save2update(mydata2actual, actualMeasureMapper, actualMeasureMachineMapper, "actual");


                                                if (isParent) {
                                                    parentIdBudget = tmpidBudget;
                                                    parentIdPlan = tmpidPlan;
                                                    parentIdActual = tmpidActual;
                                                }
                                            }
                                        }
                                    })
                            .build();
            ReadSheet readSheet3 =
                    EasyExcel.readSheet(4)
                            .head(DataOther.class)
                            .registerReadListener(
                                    new ReadListener<DataOther>() {
                                        /** 单次缓存的数据量 */
                                        public static final int BATCH_COUNT = 100;

                                        public int start = -1;
                                        /** 临时存储 */
                                        private List<DataOther> cachedDataList =
                                                ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

                                        @Override
                                        public void invoke(DataOther data, AnalysisContext context) {

                                            if (data.getSort() != null) {
                                                if (data.getSort().length() > 5) {
                                                    if (data.getSort().substring(0, 5).equals("工程名称：")) {
                                                        start = 0;
                                                    }
                                                } else if (data.getSort().length() > 0) {
                                                    if (data.getSort().substring(0, 1).equals("/")) {
                                                        start = -1;
                                                    }
                                                } else if (data.getSort().length() == 0) {
                                                    start = -1;
                                                }
                                            }
                                            if (start != -1) {
                                                start++;
                                            } else {
                                                return;
                                            }
                                            if (start > 3) { // 因为从1开始的
                                                cachedDataList.add(data);
                                                log.info("{}", data);
                                                if (cachedDataList.size() >= BATCH_COUNT) {
                                                    saveData();
                                                    // 存储完成清理 list
                                                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                                                }
                                            }
                                        }

                                        @Override
                                        public void doAfterAllAnalysed(AnalysisContext context) {
                                            saveData();
                                        }

                                        /** 加上存储数据库 */
                                        private void saveData() {
                                            String parentId = "";
                                            for (int i = 0; i < cachedDataList.size(); i++) {
                                                DataOther mydata = cachedDataList.get(i);
                                                BudgetOther mydata2 = new BudgetOther();

                                                mydata2.setName(mydata.getName());
                                                if (NumberUtils.isCreatable(mydata.getCost())) {
                                                    mydata2.setCost(new BigDecimal(mydata.getCost()));
                                                }

                                                parentId = getSnowFlake().nextId() + "";
                                                mydata2.setOtherId(parentId);
                                                mydata2.setParentId(projectId);
                                                mydata2.setOwnId(projectId);
                                                sortBudget[0]++;
                                                BigDecimal sortBudgetTmp = new BigDecimal(sortBudget[0]);
                                                mydata2.setSort(sortBudgetTmp);
                                                // budgetOtherService.save3update(mydata2, that2);
                                            }
                                        }
                                    })
                            .build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(readSheet1, readSheet2, readSheet3);
        } catch (Exception e) {
            throw e;
        }
        lockTable.setBudget(sortBudget[0]);
        lockTableMapper.updateById(lockTable);
    }
}
