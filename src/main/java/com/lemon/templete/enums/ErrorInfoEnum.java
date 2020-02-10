package com.lemon.templete.enums;

/**
 * 描述：错误信息枚举类
 * @author 汤中流
 * @date 2020/01/11
 */
public enum ErrorInfoEnum {
    // region 共通
    UNKNOWN_SYSTEM_ERROR("00000", "未知系统错误"),
    TARGET_NOT_FOUND("00001", "操作对象不存在"),
    RESULT_NOT_FOUND("00002", "结果查询为空"),
    // endregion

    // region 登录
    USER_NAME_OR_PASSWORD_ERROR("10001", "用户名或密码输入错误"),

    USER_NOT_LOGIN("11001", "用户未登录"),
    USER_OTHER_AUTHENTICATION_ERROR("11999", "其他认证错误"),

    USER_ACCESS_DENIED("12001", "用户访问权限不足"),
    USER_STATUS_OFF("12998", "账户已被禁用"),
    USER_OTHER_ACCESS_DENIED("12999", "其他授权错误"),

    USER_LOGIN_OTHER_ERROR("19999", "登录失败");
    // endregion

    // 错误码
    private String code;
    // 错误信息
    private String message;
    private ErrorInfoEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
