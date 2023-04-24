package com.pidan.exception;


import com.alibaba.druid.sql.ast.statement.SQLLateralViewTableSource;
import com.pidan.common.Ressult;
import com.pidan.model.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 自定义异常类
 *
 * @author 黄大头
 * @date 2023年04月22日 19:29
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalException {

    /**
     * 异常处理方法
     *
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Ressult<String> exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        if (exception.getMessage().contains("Duplicate entry")) {
            String[] splite = exception.getMessage().split(" ");
            String mes = splite[2] + "已存在";
            return Ressult.error(mes);
        }
        return Ressult.error("未知错误");
    }

}