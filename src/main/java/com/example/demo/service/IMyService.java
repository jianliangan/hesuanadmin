package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Base;

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
}
