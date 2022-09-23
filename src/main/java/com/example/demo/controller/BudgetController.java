package com.example.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.controller.common.BaseController;
import com.example.demo.controller.common.DataDivision;
import com.example.demo.controller.common.DataMeasure;
import com.example.demo.controller.common.DataOther;
import com.example.demo.controller.common.storage.StorageService;
import com.example.demo.entity.Construction;
import com.example.demo.entity.budget.BudgetDivision;
import com.example.demo.entity.budget.BudgetMeasure;
import com.example.demo.entity.budget.BudgetOther;
import com.example.demo.service.IMyService;
import com.example.demo.service.IProjectService;
import com.example.demo.service.budget.IBudgetDivisionService;
import com.example.demo.service.budget.IBudgetMeasureService;
import com.example.demo.service.budget.IBudgetOtherService;
import com.example.demo.service.common.WrapperOpt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/budget")
public class BudgetController extends BaseController<Construction> {
  @Autowired private ApplicationContext applicationContext;
  private final StorageService storageService;
  @Autowired private IBudgetDivisionService budgetDivisionService;
  @Autowired private IBudgetMeasureService budgetMeasureService;
  @Autowired private IBudgetOtherService budgetOtherService;
  @Autowired private IProjectService projectService;

  @Autowired
  public BudgetController(StorageService storageService) {
    this.storageService = storageService;
  }

  @Override
  protected IMyService fetchService() {
    return null;
  }

  @Override
  protected WrapperOpt fetchWrapper(HttpServletRequest request) {
    return null;
  }

  @Override
  protected String commonPrePushCheck(HttpServletRequest request) {
    return null;
  }

  @Override
  protected String commonPreFetchCheck(HttpServletRequest request) {

    if (request.getParameter("ownId") == null || request.getParameter("ownId").length() == 0)
      return "没有选中项目";
    return null;
  }

  protected IService fetchService2() {
    return null;
  }

  @PostMapping("/import")
  public ResData getGeneral2(
      @RequestParam("file") MultipartFile file,
      RedirectAttributes redirectAttributes,
      HttpServletRequest request) {
    // log.info("路徑是{}", System.getProperty("user.dir"));
    // 上传excel
    String err = null;
    err = commonPreFetchCheck(request);
    if (err == null) {
      String ownId = request.getParameter("ownId") == null ? "" : request.getParameter("ownId");
      String selectId =
          request.getParameter("selectId") == null ? "" : request.getParameter("selectId");

      String filePath = storageService.store(file);
      // 解析
      log.info("上传文件接收{}", filePath);

      log.info("存储数据库成功！");
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
                        String parentId = "";
                        for (int i = 0; i < cachedDataList.size(); i++) {
                          DataDivision mydata = cachedDataList.get(i);
                          BudgetDivision mydata2 = new BudgetDivision();

                          mydata2.setName(mydata.getName());
                          mydata2.setDistinction(mydata.getDistinction());
                          mydata2.setUnit(mydata.getUnit());

                          if (NumberUtils.isCreatable(mydata.getWorkAmount())) {
                            mydata2.setWorkAmount(new BigDecimal(mydata.getWorkAmount()));
                          }
                          if (NumberUtils.isCreatable(mydata.getSynthesisUnitprice())) {
                            mydata2.setSynthesisUnitprice(
                                new BigDecimal(mydata.getSynthesisUnitprice()));
                          }
                          log.info(
                              "wowoowwo{},{}",
                              mydata.getSynthesisSumprice(),
                              NumberUtils.isCreatable(mydata.getSynthesisSumprice()));
                          if (NumberUtils.isCreatable(mydata.getSynthesisSumprice())) {
                            mydata2.setSynthesisSumprice(
                                new BigDecimal(mydata.getSynthesisSumprice()));
                          }
                          if (mydata.getCode() != null && mydata.getCode().length() > 0) {
                            mydata2.setCode(mydata.getCode());
                            mydata2.setDivisionId(getSnowFlake().nextId() + "");
                            mydata2.setParentId(parentId);
                          } else {
                            parentId = getSnowFlake().nextId() + "";
                            mydata2.setDivisionId(parentId);
                            mydata2.setParentId(selectId);
                          }
                          mydata2.setOwnId(selectId);
                          mydata2.setSort(new BigDecimal(i + 1));
                          budgetDivisionService.save(mydata2);
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
                        String parentId = "";
                        for (int i = 0; i < cachedDataList.size(); i++) {
                          DataMeasure mydata = cachedDataList.get(i);
                          BudgetMeasure mydata2 = new BudgetMeasure();

                          mydata2.setName(mydata.getName());
                          mydata2.setDistinction(mydata.getDistinction());
                          mydata2.setUnit(mydata.getUnit());
                          if (NumberUtils.isCreatable(mydata.getWorkAmount())) {
                            mydata2.setWorkAmount(new BigDecimal(mydata.getWorkAmount()));
                          }
                          if (NumberUtils.isCreatable(mydata.getSynthesisUnitprice())) {
                            mydata2.setSynthesisUnitprice(
                                new BigDecimal(mydata.getSynthesisUnitprice()));
                          }
                          if (NumberUtils.isCreatable(mydata.getSynthesisSumprice())) {
                            mydata2.setSynthesisSumprice(
                                new BigDecimal(mydata.getSynthesisSumprice()));
                          }
                          if (mydata.getCode() != null && mydata.getCode().length() > 0) {
                            mydata2.setCode(mydata.getCode());
                            mydata2.setMeasureId(getSnowFlake().nextId() + "");
                            mydata2.setParentId(parentId);
                          } else {
                            parentId = getSnowFlake().nextId() + "";
                            mydata2.setMeasureId(parentId);
                            mydata2.setParentId(selectId);
                          }
                          mydata2.setOwnId(selectId);
                          mydata2.setSort(new BigDecimal(i + 1));
                          budgetMeasureService.save(mydata2);
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
                          mydata2.setParentId(selectId);
                          mydata2.setOwnId(selectId);

                          mydata2.setSort(new BigDecimal(i + 1));
                          budgetOtherService.save(mydata2);
                        }
                      }
                    })
                .build();
        // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
        excelReader.read(readSheet1, readSheet2, readSheet3);
      }
    }
    ResData resData = new ResData();
    resData.setCode("200");
    if (err != null) {
      resData.setErr(err);
    }
    resData.setData("");
    resData.setMessage("");
    return resData;
  }
}
