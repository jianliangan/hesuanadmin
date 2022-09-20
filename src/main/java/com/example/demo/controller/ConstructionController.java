package com.example.demo.controller;

import com.example.demo.controller.common.BaseController;
import com.example.demo.entity.Construction;
import com.example.demo.service.IConstructionService;
import com.example.demo.service.IMyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/construction")
public class ConstructionController extends BaseController<Construction> {
  @Autowired private IConstructionService constructionService;

  @Override
  protected IMyService fetchService() {
    return constructionService;
  }

  @Override
  protected String commonPrePushCheck(HttpServletRequest request) {
    return null;
  }

  @Override
  protected String commonPreFetchCheck(HttpServletRequest request) {
    return null;
  }

  @Override
  protected WrapperOpt fetchWrapper(HttpServletRequest request) {
    WrapperOpt wrapperOpt = new WrapperOpt();
    wrapperOpt.orderIsAsc = true;
    wrapperOpt.orderCondition = true;
    wrapperOpt.orderColumn = new ArrayList<>();
    wrapperOpt.orderColumn.add("sort");
    return wrapperOpt;
  }
}
