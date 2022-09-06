package com.example.demo.controller.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Base;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class BaseController<T extends Base> {
  protected int pageSize = 3;

  @Data
  protected class ResData {
    String code;
    String err;
    String message;
    Object data;
  }
  ;

  @Data
  protected class PageData {
    long itemTotal;
    long pageSize;
    Object list;
  }

  @GetMapping("/fetch")
  public ResData getGeneral(HttpServletRequest request) {
    if (getService() == null) {
      return null;
    }
    int pageIndex =
        Integer.parseInt(request.getParameter("page") == null ? "0" : request.getParameter("page"));

    // List<T> list = getService().list();

    Page<T> page = new Page(pageIndex, 2);
    IPage userPage = getService().page(page, null);
    System.out.println(",,,,,,,,,," + userPage);
    // userPage.getRecords().forEach(System.out::println);
    List<T> list = userPage.getRecords();
    PageData pageData = new PageData();
    pageData.setItemTotal(userPage.getTotal());
    pageData.setPageSize(userPage.getSize());
    pageData.setList(list);
    ResData resData = new ResData();
    resData.setCode("200");
    resData.setData(pageData);
    resData.setMessage("");
    return resData;
  }

  @PostMapping("/push")
  public String addGeneral(@RequestBody T row) {
    if (getService() == null) {
      return "no service";
    }
    if (row.getCmd().equals("edit")) {
      getService().updateById(row);
    } else if (row.getCmd().equals("add")) {
      getService().save(row);
    } else if (row.getCmd().equals("delete")) {
      getService().removeById(row);
    }
    return "success";
  }

  protected abstract IService getService();
}
