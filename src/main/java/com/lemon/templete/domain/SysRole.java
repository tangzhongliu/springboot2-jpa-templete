package com.lemon.templete.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 描述：角色表
 * @author 汤中流
 * @date 2019/08/05
 */
@Entity
@Table(name = "sys_role")
public class SysRole extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    private String roleName;

    private String roleRemark;

    @ManyToMany(mappedBy = "sysRoles", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<SysUser> sysUsers = new LinkedHashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_menu_rel",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"))
    private Set<SysMenu> sysMenus = new LinkedHashSet<>();;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleRemark() {
        return roleRemark;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark;
    }

    public Set<SysUser> getSysUsers() {
        return sysUsers;
    }

    public void setSysUsers(Set<SysUser> sysUsers) {
        this.sysUsers = sysUsers;
    }

    public Set<SysMenu> getSysMenus() {
        return sysMenus;
    }

    public void setSysMenus(Set<SysMenu> sysMenus) {
        this.sysMenus = sysMenus;
    }
}
