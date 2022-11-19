package com.example.demo.entity.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.controller.common.ITreeMenuEach;
import com.example.demo.entity.Base;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@Data
public class Setting extends Base {
    @TableId(type = IdType.INPUT)
    private String item;

    private String content;

    private BigDecimal sort;
    private String ownId;
    private String parentId;
    private Integer tag;

    @Override
    public Object fetchPrimeId() {
        return item;
    }

    @Override
    public void pushPrimeId(Object value) {
        item = value.toString();
    }

    @Override
    public Object fetchParentId() {
        return parentId;
    }

    public static void eachList(JSONArray parent, JSONObject tmp2, ITreeMenuEach treeMenuEach) {
        for (int i = 0; i < parent.size(); i++) {
            JSONObject line = parent.getJSONObject(i);
            if (line.containsKey("children")) {
                eachList(line.getJSONArray("children"), tmp2, treeMenuEach);

            }
            JSONArray jsonObject = (JSONArray) line.get("children");
            if (jsonObject != null) {
                if (jsonObject.size() == 0) {
                    parent.remove(i);
                    i--;
                    continue;
                }
            }
            if (line.get("component") != null) {
                int tmp = treeMenuEach.Each(parent, line, tmp2, i);
                if (tmp != -2) {
                    i = tmp;
                }
            }

        }
    }
}
