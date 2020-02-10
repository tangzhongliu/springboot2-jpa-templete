package com.lemon.templete.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 描述：用户角色关联表
 * @author 汤中流
 * @date 2019/08/05
 */
@Entity
@Table(name = "sys_role_menu_rel")
public class SysRoleMenuRel extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "role_id")
    private Integer roleId;
    @Column(name = "menu_id")
    private Integer menuId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
