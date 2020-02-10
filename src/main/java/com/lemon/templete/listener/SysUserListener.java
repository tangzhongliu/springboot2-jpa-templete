package com.lemon.templete.listener;

import com.lemon.templete.constant.Constant;
import com.lemon.templete.domain.SysUser;
import com.lemon.templete.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * 描述：监听器
 * @author 汤中流
 * @date 2019/07/24
 */
@WebListener
public class SysUserListener implements ServletContextListener {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private Constant constant;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // region 监听器初始化的时候，加载DB中的所有用户信息，并将其放入Redis缓存中
        // 适用场景: 一般来说，如果某些数据，变化频率小，并且会经常被调用使用的话，会考虑在这个时候
        //          将该数据写入Redis中，便于之后调用，提高效率
        // 此处将用户信息写入缓存，只是单纯的为了展示如何实现Redis下的，[删除]，[插入]。在SysUserServiceImpl中有[查询]的例子
        String allUserKey = constant.getAllUser();
        // 查询数据库所有的用户
        List<SysUser> sysUserList = sysUserService.findAll();
        // 清除缓存中的用户数据
        redisTemplate.delete(allUserKey);
        // 将数据存放到redis缓存中
        redisTemplate.opsForList().leftPushAll(allUserKey, sysUserList);
        // endregion
        logger.info("--------------->>> SysUserListener 上下文初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("--------------->>> SysUserListener 上下文销毁");
    }
}
