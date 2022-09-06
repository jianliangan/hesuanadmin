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
import com.example.demo.controller.common.storage.StorageService;
import com.example.demo.entity.Construction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/budget")
public class BudgetController extends BaseController<Construction> {
  @Autowired private ApplicationContext applicationContext;
  private final StorageService storageService;

  @Autowired
  public BudgetController(StorageService storageService) {
    this.storageService = storageService;
  }

  @Override
  protected IService getService() {
    return null;
  }

  protected IService getService2() {
    return null;
  }

  @GetMapping("/import")
  public ResData getGeneral2(
      @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
    // log.info("路徑是{}", System.getProperty("user.dir"));
    // 上传excel
    storageService.store(file);
    // 解析
    String filename = "upload/采暖通风工程-中型-06.xlsx";
    Resource fileResource = storageService.loadAsResource(filename);

    String filePath = "";
    try {
      filePath = fileResource.getFile().getPath();
      System.out.println(filePath);
    } catch (IOException e) {
      e.printStackTrace();
    }

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
                      log.info("{}条数据，开始存储数据库！", cachedDataList.size());
                      log.info("存储数据库成功！");
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
                      log.info("{}条数据，开始存储数据库！", cachedDataList.size());
                      log.info("存储数据库成功！");
                    }
                  })
              .build();
      // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
      excelReader.read(readSheet1, readSheet2);
    }

    ResData resData = new ResData();
    resData.setCode("200");
    resData.setData("");
    resData.setMessage("");
    return resData;
  }
}
