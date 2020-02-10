package com.lemon.templete.service.impl;

import com.lemon.templete.constant.Constant;
import com.lemon.templete.domain.SysUser;
import com.lemon.templete.repository.BaseRepository;
import com.lemon.templete.repository.SysUserRepository;
import com.lemon.templete.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 描述: 用户服务层实现类
 * @author 汤中流
 * @date 2019/07/12
 *
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private Constant constant;

    @Override
    public BaseRepository<SysUser> getRepository() {
        return sysUserRepository;
    }

    /**
     * 获取指定用户名对应的用户信息
     * @param userName 用户名
     * @return 用户信息
     */
    @Override
    public SysUser findByUserName(String userName) {
        return sysUserRepository.findByUserName(userName);
    }

    // region ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 以下是【利用Redis做缓存，获取数据的例子】的参考方法 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    /**
     * 获取所有用户信息从Redis和DB
     * (先从redis中取，没有的话去DB中取)
     * @return 所有用户信息
     */
    @Override
    public List<SysUser> findAllFromRedisAndDb() {
        try {
            String allUserKey = constant.getAllUser();
            // Step1.查询redis缓存中的数据
            List<SysUser> sysUsers = redisTemplate.opsForList().range(allUserKey, 0, -1);
            if (sysUsers != null && sysUsers.size() > 0) {
                return sysUsers;
            }
            // Step2.查询数据库中的数据
            List<SysUser> sysUsersInDB = sysUserRepository.findAll();
            if (sysUsersInDB != null && sysUsersInDB.size() > 0) {
                // Step3.将数据插入Redis缓存中
                redisTemplate.opsForList().leftPushAll(allUserKey, sysUsersInDB);
            }
            return sysUsersInDB;
        } catch (Exception e) {
            logger.error("method [findAllFromRedisAndDb] error", e);
            return Collections.EMPTY_LIST;
        }
    }
    // endregion
}
