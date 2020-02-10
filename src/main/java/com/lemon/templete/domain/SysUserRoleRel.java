package com.lemon.templete.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 描述：用户角色关联表
 * @author 汤中流
 * @date 2019/08/05
 */
@Entity
@Table(name = "sys_user_role_rel")
public class SysUserRoleRel extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "role_id")
    private Integer roleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
