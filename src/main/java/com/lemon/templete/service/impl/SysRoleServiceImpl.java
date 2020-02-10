package com.lemon.templete.service.impl;

import com.lemon.templete.domain.SysRole;
import com.lemon.templete.repository.BaseRepository;
import com.lemon.templete.repository.SysRoleRepository;
import com.lemon.templete.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述: 角色服务层实现类
 * @author 汤中流
 * @date 2019/08/05
 *
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {
    @Resource
    private SysRoleRepository sysRoleRepository;
    @Override
    public BaseRepository<SysRole> getRepository() {
        return sysRoleRepository;
    }
}
