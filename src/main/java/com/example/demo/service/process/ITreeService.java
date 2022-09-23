package com.example.demo.service.process;

import com.example.demo.entity.Base;
import com.example.demo.service.IMyService;
import com.example.demo.service.common.ISumReportService;
import com.example.demo.service.common.PageData;

public interface ITreeService {
  public <T extends Base & ISumReportService> PageData treeWithPrice(
      String selectId, String ownId, IMyService myService, ITreeServiceConvert treeServiceConvert);
}
