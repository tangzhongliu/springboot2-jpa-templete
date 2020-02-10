package com.lemon.templete.enums;

/**
 * 描述：JPA动态查询用-查询类型枚举类
 * @author 汤中流
 * @date 2019/10/28
 */
public enum QueryTypeEnum {
    // 等于
    EQ,
    // 不等于
    NE,
    // 包含-模糊查询
    LIKE,
    // 前方一致-模糊查询
    START,
    // 后方一致-模糊查询
    END,
    // 小于
    LT,
    // 小于等于
    LE,
    // 大于
    GT,
    // 大于等于
    GE
}
