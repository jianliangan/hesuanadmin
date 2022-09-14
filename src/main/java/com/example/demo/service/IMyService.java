package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;

public interface IMyService<T> extends IService<T> {
  public abstract boolean checkCanDelete(T instan);
}
