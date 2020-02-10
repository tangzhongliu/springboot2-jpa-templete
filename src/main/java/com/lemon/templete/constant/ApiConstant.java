package com.lemon.templete.constant;

/**
 * 描述：RESTful API配置常量类
 * @author 汤中流
 * @date 2019/12/08
 */
public class ApiConstant {

    // region ++++ 共通API
    // 新增
    public static final String ADD = "";
    // 编辑
    public static final String MODIFY = "/{id}";
    // 删除(物理删除)
    public static final String DEL = "/{id}";
    // 编辑数据状态(逻辑删除)
    public static final String MODIFY_STATUS = "/{id}/status";
    // 获取所有数据信息
    public static final String FIND_ALL = "";
    // 获取分页数据信息
    public static final String FIND_PAGE = "/page";
    // 获取主键数据信息
    public static final String FIND_ONE = "/{id}";
    // endregion

    // region ++++ 系统用户API
    // 根目录
    public static final String SYS_USER = "/sys/users";
    // 用户登录
    public static final String SYS_USER_LOGIN = SYS_USER + "/login";
    // 用户注销
    public static final String SYS_USER_LOGOUT = SYS_USER + "/logout";
    // endregion

}
