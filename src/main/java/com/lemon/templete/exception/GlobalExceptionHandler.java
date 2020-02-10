package com.lemon.templete.exception;

import com.lemon.templete.enums.ErrorInfoEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述：全局异常处理类
 * @author 汤中流
 * @date 2019/08/02
 */
@RestControllerAdvice(basePackages = {"com.lemon.templete",})
public class GlobalExceptionHandler {
    @ExceptionHandler({BusinessException.class})
    public ExceptionResponse businessExceptionHandler(HttpServletRequest req, HttpServletResponse res, BusinessException e) throws Exception {
        res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new ExceptionResponse(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ExceptionResponse exceptionHandler(HttpServletRequest req, HttpServletResponse res, Exception e) throws Exception {
        res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new ExceptionResponse(ErrorInfoEnum.UNKNOWN_SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
