package com.lemon.templete.service;

import com.lemon.templete.domain.SysUser;

import java.util.List;

/**
 * 描述: 用户服务层接口
 * @author 汤中流
 * @date 2019/07/12
 *
 */
public interface SysUserService extends BaseService<SysUser> {

    /**
     * 获取指定用户名对应的用户信息
     * @param userName 用户名
     * @return 用户信息
     */
    SysUser findByUserName(String userName);

    // region ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 以下是【利用Redis做缓存，获取数据的例子】的参考方法 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    /**
     * 获取所有用户信息从Redis和DB
     * (先从redis中取，没有的话去DB中取)
     * @return 所有用户信息
     */
    List<SysUser> findAllFromRedisAndDb();
    // endregion
}
