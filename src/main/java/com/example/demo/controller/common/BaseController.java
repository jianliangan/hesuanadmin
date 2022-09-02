package com.example.demo.controller.common;

import lombok.Data;

public class BaseController {
    @Data
    public class ResData {
        String code;
        public String message;
        Object data;

    };
}
