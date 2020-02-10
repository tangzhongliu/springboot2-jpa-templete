package com.lemon.templete.service.impl;

import com.lemon.templete.domain.SysMenu;
import com.lemon.templete.repository.BaseRepository;
import com.lemon.templete.repository.SysMenuRepository;
import com.lemon.templete.service.SysMenuService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述: 角色服务层实现类
 * @author 汤中流
 * @date 2019/08/05
 *
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu> implements SysMenuService {
    @Resource
    private SysMenuRepository sysMenuRepository;

    @Override
    public BaseRepository<SysMenu> getRepository() {
        return sysMenuRepository;
    }

    @Override
    public List<SysMenu> findAllOrderByMenuUrlDesc() {
        Sort sort = new Sort(Sort.Direction.DESC, "menuUrl");

        return sysMenuRepository.findAll(sort);
    }
}
