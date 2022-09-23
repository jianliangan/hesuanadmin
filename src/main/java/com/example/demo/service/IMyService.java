package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Base;
import com.example.demo.service.common.ISumReportService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface IMyService<T extends Base> extends IService<T> {
  public abstract boolean checkCanDelete(T instan);

  default Base treeBusinessParse(Base d0, List<Base> wflist) {

    List list = new ArrayList<Base>();

    for (int i = 0; i < wflist.size(); i++) {
      Base value = wflist.get(i);
      if (value.fetchParentId().equals(d0.fetchPrimeId())) {

        wflist.remove(i);
        list.add(treeBusinessParse(value, wflist));
        i = -1;
      }
    }
    d0.setChildren(list);
    return d0;
  }

  default void treeBusinessParse(Base d0, List<Base> wflist, List<Base> resList) {

    List list = new ArrayList<Base>();

    for (int i = 0; i < wflist.size(); i++) {
      Base value = wflist.get(i);
      if (value.fetchParentId().equals(d0.fetchPrimeId())) {
        wflist.remove(i);
        list.add(value);
        treeBusinessParse(value, wflist, resList);
        i = -1;
      }
    }
    resList.addAll(list);
  }

  default <T extends Base & ISumReportService> T treeProjectSum(T d0, List<T> wflist) {

    BigDecimal workAmount = d0.fetchWorkAmount();
    BigDecimal synthesisUnitprice = d0.fetchSynthesisUnitprice();
    BigDecimal synthesisSumprice = d0.fetchSynthesisSumprice();
    List list = new ArrayList<T>();
    for (int i = 0; i < wflist.size(); i++) {
      T value = wflist.get(i);
      if (value.fetchParentId().equals(d0.fetchPrimeId())) {
        wflist.remove(i);

        T tmp = treeProjectSum(value, wflist);
        workAmount = workAmount.add(tmp.fetchWorkAmount());
        synthesisUnitprice = synthesisUnitprice.add(tmp.fetchSynthesisUnitprice());
        synthesisSumprice = synthesisSumprice.add(tmp.fetchSynthesisSumprice());
        list.add(tmp);
        ;
        i = -1;
      }
    }
    d0.pushWorkAmount(workAmount);
    d0.pushSynthesisUnitprice(synthesisUnitprice);
    d0.pushSynthesisSumprice(synthesisSumprice);
    d0.setChildren(list);

    return d0;
  }
}
