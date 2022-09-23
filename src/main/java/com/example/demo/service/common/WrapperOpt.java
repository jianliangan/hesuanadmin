package com.example.demo.service.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;
import java.util.Map;

public class WrapperOpt {
  public WrapperOpt() {}
  ;

  public boolean orderCondition;
  public boolean orderIsAsc;
  public List<String> orderColumn;
  public Map<String, String> wheres;
  public Map<String, List<String>> ins;

  public static <T> QueryWrapper parseWrapperOption(WrapperOpt wrapperOpt) {
    QueryWrapper<T> wrapper = null;
    if (wrapperOpt != null && (wrapperOpt.orderColumn != null || wrapperOpt.wheres != null)) {
      wrapper = new QueryWrapper<T>();
      if (wrapperOpt.orderColumn != null)
        wrapper.orderBy(wrapperOpt.orderCondition, wrapperOpt.orderIsAsc, wrapperOpt.orderColumn);
      if (wrapperOpt.wheres != null) wrapper.allEq(wrapperOpt.wheres);
      if (wrapperOpt.ins != null && wrapperOpt.ins.size() > 0) {
        for (Map.Entry<String, List<String>> entry : wrapperOpt.ins.entrySet()) {
          String mapKey = entry.getKey();
          wrapper.in(mapKey, entry.getValue().toArray());
        }
      }
    }
    return wrapper;
  }
}
