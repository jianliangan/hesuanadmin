package com.example.demo.controller.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Base;
import com.example.demo.service.IMyService;
import com.example.demo.service.common.PageData;
import com.example.demo.service.common.WrapperOpt;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseController<T extends Base> {
  protected int pageSize = 20;
  private static final SnowFlake snowFlake = new SnowFlake(1, 1);

  public static SnowFlake getSnowFlake() {
    return snowFlake;
  }

  @Data
  protected class ResData {
    String code;
    String err;
    String message;
    Object data;
  }

  @GetMapping("/fetch")
  public ResData getGeneral(HttpServletRequest request) {
    String err = null;
    PageData pageData = new PageData();
    if (fetchService() == null) {
      err = "服务器错误";
    }
    if (err == null) {
      err = commonPreFetchCheck(request);

      if (err == null) {

        int pageIndex =
            Integer.parseInt(
                request.getParameter("page") == null ? "0" : request.getParameter("page"));

        pageIndex = pageIndex == 0 ? 1 : pageIndex;
        // List<T> list = fetchService().list();

        Page<T> page = new Page(pageIndex, pageSize);
        WrapperOpt wrapperOpt = fetchWrapper(request);
        IPage userPage = fetchService().page(page, WrapperOpt.parseWrapperOption(wrapperOpt));
        System.out.println(",,,,,,,,,," + userPage);
        // userPage.getRecords().forEach(System.out::println);
        List<T> list = userPage.getRecords();
        int itemOffset = (pageIndex - 1) * pageSize;
        for (int i = 0; i < list.size(); i++) {
          list.get(i).setSortR(itemOffset + i + 1);
        }

        pageData.setItemTotal(userPage.getTotal());
        pageData.setPageSize(userPage.getSize());
        pageData.setList(list);
      }
    }
    ResData resData = new ResData();
    resData.setCode("200");
    if (err != null) {
      resData.setErr(err);
    }
    resData.setData(pageData);
    resData.setMessage("");
    return resData;
  }

  @PostMapping("/push")
  public ResData addGeneral(@RequestBody List<T> rows, HttpServletRequest request) {
    String err = null;
    err = commonPrePushCheck(request);
    String primeId = "";
    if (err == null) {
      if (fetchService() == null) {
        err = "服务器错误";
      }
      if (rows.size() > 2) {
        err = "请求错误：条数太多";
      }
      if (err == null) {
        for (Integer i = 0; i < rows.size(); i++) {
          if (rows.get(i).getCmd().equals("edit")) {
            fetchService().updateById(rows.get(i));
          } else if (rows.get(i).getCmd().equals("add")) {

            primeId = getSnowFlake().nextId() + "";
            rows.get(i).pushPrimeId(primeId);

            System.out.println(primeId);
            fetchService().save(rows.get(i));
          } else if (rows.get(i).getCmd().equals("delete")) {
            if (!fetchService().checkCanDelete(rows.get(i))) {

              err = "删除错误：可能是存在子项，或者存在数据";
              break;
            }
            fetchService().removeById(rows.get(i));
          }
        }
      }
    }
    ResData resData = new ResData();
    resData.setCode("200");
    if (err != null) {
      resData.setErr(err);
    }
    resData.setMessage("");
    resData.setData(primeId);
    return resData;
  }

  /**
   * 先根据where确定数据范围，然后将这片数据变成树 参数只接收rootId,<where条件> 其中rootId表示范围内从哪里算根 <where条件>用来确定范围</where条件>
   *
   * @param request
   * @return
   */
  @GetMapping("/tree")
  public ResData getTree(HttpServletRequest request) {
    if (fetchService() == null) {
      return null;
    }
    String rootId = request.getParameter("rootId"); // 只用树做对比不能用在where后面
    List<T> relist = null;
    int pageIndex = 1;
    String err = null;
    IPage userPage = null;
    err = commonPreFetchCheck(request);
    PageData pageData = null;
    if (err == null) {

      Page<T> page = new Page(pageIndex, 500);
      WrapperOpt wrapperOpt = fetchWrapper(request);

      userPage = fetchService().page(page, WrapperOpt.parseWrapperOption(wrapperOpt));
      System.out.println(",,,,,,,,,," + userPage);
      // userPage.getRecords().forEach(System.out::println);
      List<T> list = userPage.getRecords();
      int itemOffset = (pageIndex - 1) * pageSize;

      // 树形

      relist = new ArrayList<T>();
      for (int i = 0; i < list.size(); i++) {
        T value = list.get(i);
        if (value.fetchParentId().toString().equals(rootId)) {
          list.remove(i);
          relist.add((T) fetchService().treeBusinessParse(value, list));
          i = -1;
        }
      }

      pageData = new PageData();
      pageData.setItemTotal(userPage.getTotal());
      pageData.setPageSize(userPage.getSize());
      pageData.setList(relist);
    }
    ResData resData = new ResData();
    resData.setCode("200");
    if (err != null) {
      resData.setErr(err);
    }
    resData.setData(pageData);
    resData.setMessage("");
    return resData;
  }

  protected abstract IMyService fetchService();

  protected abstract WrapperOpt fetchWrapper(HttpServletRequest request);

  protected abstract String commonPrePushCheck(HttpServletRequest request);

  protected abstract String commonPreFetchCheck(HttpServletRequest request);
}
