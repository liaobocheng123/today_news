package com.lbc.common.exception;

import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.common.enums.AppHttpCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice//控制器增强
@Log4j2
public class ExceptionCatch {
    
    //捕获Exception此类异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception) {
        exception.printStackTrace();
        //记录日志
        log.error("统一异常类catch exception:{}", exception.getMessage());
        //返回通用异常
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
    }
}