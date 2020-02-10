package com.lemon.templete.utils;

/**
 * 描述：服务层工具类
 * @author 汤中流
 * @date 2019/10/10
 */
public class ServiceUtils {

    /**
     * 生成动态查询条件Key
     * 生成规则：attribute:queryType
     * @param attribute 查询属性
     * @param queryType 查询类型
     * @return 查询Key（例如："username:LIKE"）
     */
    public static String getQueryKey(String attribute, String queryType) {
        // 例："userName:NE"
        StringBuilder sb = new StringBuilder();
        sb.append(attribute);
        sb.append(":");
        sb.append(queryType);
        return sb.toString();
    }
}
