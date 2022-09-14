package com.example.demo.controller.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Base;
import com.example.demo.service.IMyService;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

  @Data
  protected class PageData {
    long itemTotal;
    long pageSize;
    Object list;
  }

  @GetMapping("/fetch")
  public ResData getGeneral(HttpServletRequest request) {
    String err = null;
    PageData pageData = new PageData();
    if (getService() == null) {
      err = "服务器错误";
    }
    if (err == null) {
      err = commonPreFetchCheck(request);

      if (err == null) {

        int pageIndex =
            Integer.parseInt(
                request.getParameter("page") == null ? "0" : request.getParameter("page"));
        int projectId =
            Integer.parseInt(
                request.getParameter("projectId") == null
                    ? "0"
                    : request.getParameter("projectId"));
        pageIndex = pageIndex == 0 ? 1 : pageIndex;
        // List<T> list = getService().list();

        Page<T> page = new Page(pageIndex, pageSize);
        WrapperOpt wrapperOpt = getWrapper(request);
        IPage userPage = getService().page(page, parseWrapperOption(wrapperOpt));
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
    if (err == null) {
      if (getService() == null) {
        err = "服务器错误";
      }
      if (rows.size() > 2) {
        err = "请求错误：条数太多";
      }
      if (err == null) {
        for (Integer i = 0; i < rows.size(); i++) {
          if (rows.get(i).getCmd().equals("edit")) {
            getService().updateById(rows.get(i));
          } else if (rows.get(i).getCmd().equals("add")) {
            getService().save(rows.get(i));
          } else if (rows.get(i).getCmd().equals("delete")) {
            if (!getService().checkCanDelete(rows.get(i))) {

              err = "删除错误：可能是存在子项，或者存在数据";
              break;
            }
            getService().removeById(rows.get(i));
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
    return resData;
  }

  private T treeBusinessParse(T d0, List<T> wflist) {

    List list = new ArrayList<T>();

    for (int i = 0; i < wflist.size(); i++) {
      T value = wflist.get(i);
      if (value.getParentId().equals(d0.getPrimeId())) {

        wflist.remove(i);
        list.add(treeBusinessParse(value, wflist));
        i = -1;
      }
    }
    d0.setChildren(list);
    return d0;
  }

  private QueryWrapper parseWrapperOption(WrapperOpt wrapperOpt) {
    QueryWrapper<T> wrapper = null;
    if (wrapperOpt != null && (wrapperOpt.orderColumn != null || wrapperOpt.wheres != null)) {
      wrapper = new QueryWrapper<T>();
      if (wrapperOpt.orderColumn != null)
        wrapper.orderBy(wrapperOpt.orderCondition, wrapperOpt.orderIsAsc, wrapperOpt.orderColumn);
      if (wrapperOpt.wheres != null) wrapper.allEq(wrapperOpt.wheres);
    }
    return wrapper;
  }

  /**
   * 先根据where确定数据范围，然后将这片数据变成树 参数只接收rootId,<where条件> 其中rootId表示范围内从哪里算根 <where条件>用来确定范围</where条件>
   *
   * @param request
   * @return
   */
  @GetMapping("/tree")
  public ResData getTree(HttpServletRequest request) {
    if (getService() == null) {
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
      WrapperOpt wrapperOpt = getWrapper(request);

      userPage = getService().page(page, parseWrapperOption(wrapperOpt));
      System.out.println(",,,,,,,,,," + userPage);
      // userPage.getRecords().forEach(System.out::println);
      List<T> list = userPage.getRecords();
      int itemOffset = (pageIndex - 1) * pageSize;

      // 树形

      relist = new ArrayList<T>();
      for (int i = 0; i < list.size(); i++) {
        T value = list.get(i);
        if (value.getParentId().toString().equals(rootId)) {
          list.remove(i);
          relist.add(treeBusinessParse(value, list));
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

  protected abstract IMyService getService();

  protected abstract WrapperOpt getWrapper(HttpServletRequest request);

  protected abstract String commonPrePushCheck(HttpServletRequest request);

  protected abstract String commonPreFetchCheck(HttpServletRequest request);

  protected class WrapperOpt {
    public WrapperOpt() {}
    ;

    public boolean orderCondition;
    public boolean orderIsAsc;
    public List<String> orderColumn;
    public Map<String, String> wheres;
  }
}
