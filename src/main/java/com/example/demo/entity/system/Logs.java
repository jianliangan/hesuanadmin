package com.example.demo.entity.system;

import com.example.demo.entity.Base;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Logs extends Base {
    private String userName;

    private String ip;
    private String dist;
    private String browser;
    private String os;
    private String status;
    private String action;
    private String actionInfos;
    private String params;
    private String body;
    private String datetime;

    private BigDecimal sort;
    private String ownId;
    private String parentId;
    private Integer tag;

    @Override
    public Object fetchPrimeId() {
        return null;
    }

    @Override
    public void pushPrimeId(Object value) {
        return;
    }

    @Override
    public Object fetchParentId() {
        return null;
    }
}
