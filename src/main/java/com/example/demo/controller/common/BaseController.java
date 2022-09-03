package com.example.demo.controller.common;

import lombok.Data;

public class BaseController {
    protected int pageSize = 3;

    @Data
    protected class ResData {
        String code;
        String err;
        String message;
        Object data;

    };

    @Data
    protected class PageData {
        int pagenum;
        int pagesize;
        Object list;
    }
}
