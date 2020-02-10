package com.lemon.templete.service;

import com.lemon.templete.domain.SysMenu;

import java.util.List;

/**
 * 描述: 菜单服务层接口
 * @author 汤中流
 * @date 2019/08/28
 *
 */
public interface SysMenuService extends BaseService<SysMenu> {
    List<SysMenu> findAllOrderByMenuUrlDesc();
}
