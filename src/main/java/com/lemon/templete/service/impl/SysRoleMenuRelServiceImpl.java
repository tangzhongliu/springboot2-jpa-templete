package com.lemon.templete.service.impl;

import com.lemon.templete.domain.SysRoleMenuRel;
import com.lemon.templete.repository.BaseRepository;
import com.lemon.templete.repository.SysRoleMenuRelRepository;
import com.lemon.templete.service.SysRoleMenuRelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述: 角色菜单关联服务层实现类
 * @author 汤中流
 * @date 2019/08/05
 *
 */
@Service
public class SysRoleMenuRelServiceImpl extends BaseServiceImpl<SysRoleMenuRel> implements SysRoleMenuRelService {
    @Resource
    private SysRoleMenuRelRepository sysRoleMenuRelRepository;
    @Override
    public BaseRepository<SysRoleMenuRel> getRepository() {
        return sysRoleMenuRelRepository;
    }
}
