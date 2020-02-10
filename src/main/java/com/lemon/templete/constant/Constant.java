package com.lemon.templete.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 描述：自定义常量实体类（与yml文件相应属性对应）
 * @author 汤中流
 * @date 2019/09/27
 */
@Component
public class Constant {

    // Redis缓存中，存储所有用户的key
    // 这个常量的值对应yml文件中的constant.redis.key.all-user的值
    // 一般来说，需要把一些部署时可能会变化常量放到yml中，按照这种方式引用。
    // 这里的redis-key其实是不需要这么做的，因为这个值基本不会轻易改变。
    // 即使改变，也是需要程序员在程序中修改，这个值直接定义成【public static final String ALL_USER = "ALL_USER"】常量就OK。
    // 这里只是为了展示如何引用yml中的值。
    @Value("${constant.redis.key.all-user}")
    private String allUser;

    public String getAllUser() {
        return allUser;
    }

    public void setAllUser(String allUser) {
        this.allUser = allUser;
    }
}
