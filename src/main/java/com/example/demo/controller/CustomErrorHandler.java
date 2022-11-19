package com.example.demo.controller;

import com.example.demo.controller.common.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class CustomErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseController.ResData> handleConstraintViolationException(ConstraintViolationException exception,
                                                                                     ServletWebRequest webRequest) throws IOException {
//        CustomError customError = new CustomError();
//        customError.setStatus(HttpStatus.BAD_REQUEST);
//        customError.setMessage(exception.getMessage());
//        customError.addConstraintErrors(exception.getConstraintViolations());
//        return ResponseEntity.badRequest().body(customError);

        BaseController.ResData resData = new BaseController.ResData();
        resData.setCode("200");
        resData.setErr(exception.getMessage());
        resData.setData("");
        resData.setMessage("");
        return ResponseEntity.ok(resData);
        //return ResponseEntity <>(resData, HttpStatus.OK);
        //webRequest.getResponse().sendError(HttpStatus.BAD_REQUEST.value(), "dddd");
        //.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<BaseController.ResData> handleViolationException(ValidationException exception,
                                                                           ServletWebRequest webRequest) throws IOException {

        BaseController.ResData resData = new BaseController.ResData();
        resData.setCode("200");
        resData.setErr(exception.getMessage());
        resData.setData("");
        resData.setMessage("");
        return ResponseEntity.ok(resData);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<BaseController.ResData> handleViolationException(SQLIntegrityConstraintViolationException exception,
                                                                           ServletWebRequest webRequest) throws IOException {

        BaseController.ResData resData = new BaseController.ResData();
        resData.setCode("200");
        String addmsg = "";
        if (exception.getMessage().indexOf("Duplicate") != -1) {
            addmsg += "数据不能重复";
        }
        if (exception.getMessage().indexOf(".name") != -1) {
            addmsg += "，名称和特征";
        }
        resData.setErr(addmsg + exception.getMessage());
        resData.setData("");
        resData.setMessage("");
        return ResponseEntity.ok(resData);
    }
}
