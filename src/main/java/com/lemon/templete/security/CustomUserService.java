package com.lemon.templete.security;

import com.lemon.templete.domain.SysRole;
import com.lemon.templete.domain.SysUser;
import com.lemon.templete.enums.ErrorInfoEnum;
import com.lemon.templete.service.SysUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 描述: spring security自定义用户服务层实现类
 *       用于认证，授权登录用户
 * @author 汤中流
 * @date 2019/08/08
 *
 */
public class CustomUserService implements UserDetailsService {
    @Resource
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        // 查询登录用户名对应的用户信息，进行认证
        SysUser sysUser = sysUserService.findByUserName(name);
        if(sysUser == null) {
            // (错误信息统一在WebSecurityConfig中处理)
            throw new UsernameNotFoundException(null);
        }

        // 获取用户关联的所有角色
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
        Set<SysRole> sysRoles = sysUser.getSysRoles();
        if (sysRoles != null) {
            for (SysRole sysRole : sysRoles) {
                grantedAuthorityList.add(new SimpleGrantedAuthority(sysRole.getRoleName()));
            }
        }

        // 返回当前登录用户的信息（包括用户名，密码，角色列表）
        return new User(sysUser.getUserName(), sysUser.getUserPassword(), grantedAuthorityList);
    }
}
