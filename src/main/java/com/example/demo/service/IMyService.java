package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Base;

public interface IMyService<T extends Base> extends IService<T> {
  public abstract boolean checkCanDelete(T instan);
}
