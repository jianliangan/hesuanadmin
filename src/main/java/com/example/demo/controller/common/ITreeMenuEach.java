package com.example.demo.controller.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface ITreeMenuEach {
  public int Each(JSONArray parent, JSONObject line, JSONObject p, int index);
}
