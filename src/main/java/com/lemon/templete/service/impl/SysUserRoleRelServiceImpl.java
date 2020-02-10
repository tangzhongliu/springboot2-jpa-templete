package com.lemon.templete.service.impl;

import com.lemon.templete.domain.SysUserRoleRel;
import com.lemon.templete.repository.BaseRepository;
import com.lemon.templete.repository.SysUserRoleRelRepository;
import com.lemon.templete.service.SysUserRoleRelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述: 用户角色关联服务层实现类
 * @author 汤中流
 * @date 2019/08/05
 *
 */
@Service
public class SysUserRoleRelServiceImpl extends BaseServiceImpl<SysUserRoleRel> implements SysUserRoleRelService {
    @Resource
    private SysUserRoleRelRepository sysUserRoleRelRepository;
    @Override
    public BaseRepository<SysUserRoleRel> getRepository() {
        return sysUserRoleRelRepository;
    }
}
