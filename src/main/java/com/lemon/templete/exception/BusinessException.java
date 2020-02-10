package com.lemon.templete.exception;

import com.lemon.templete.enums.ErrorInfoEnum;

/**
 * 描述：业务异常
 * @author 汤中流
 * @date 2019/08/02
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String code;
    public BusinessException(ErrorInfoEnum errorInfoEnum) {
        super(errorInfoEnum.getMessage());
        this.code = errorInfoEnum.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
