package com.example.demo.service.impl.process;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Base;
import com.example.demo.entity.Project;
import com.example.demo.service.IMyService;
import com.example.demo.service.IProjectService;
import com.example.demo.service.common.ISumReportService;
import com.example.demo.service.common.PageData;
import com.example.demo.service.common.WrapperOpt;
import com.example.demo.service.process.ITreeService;
import com.example.demo.service.process.ITreeServiceConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TreeServiceImpl implements ITreeService {
  @Autowired private IProjectService projectService;

  public <T extends Base & ISumReportService> PageData treeWithPrice(
      String selectId, String ownId, IMyService myService, ITreeServiceConvert treeServiceConvert) {

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
        projectService.treeBusinessParse(value, list, tmplist);

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
    resist.get(0).setChildren(relist);
    // 做树形渲染
    myService.<T>treeProjectSum((T) resist.get(0), prelist);

    PageData pageData = new PageData();
    pageData.setItemTotal(userPage.getTotal());
    pageData.setPageSize(userPage.getSize());
    pageData.setList(resist);
    return pageData;
  }

  public <T extends Base & ISumReportService> PageData tree3CompareWithPrice(
      String selectId, String ownId, IMyService myService, ITreeServiceConvert treeServiceConvert) {

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
        projectService.treeBusinessParse(value, list, tmplist);

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
    resist.get(0).setChildren(relist);
    // 做树形渲染
    myService.<T>treeProjectSum((T) resist.get(0), prelist);

    PageData pageData = new PageData();
    pageData.setItemTotal(userPage.getTotal());
    pageData.setPageSize(userPage.getSize());
    pageData.setList(resist);
    return pageData;
  }
}
