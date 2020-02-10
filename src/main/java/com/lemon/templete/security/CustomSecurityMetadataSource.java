package com.lemon.templete.security;

import com.lemon.templete.domain.SysMenu;
import com.lemon.templete.domain.SysRole;
import com.lemon.templete.service.SysMenuService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Resource
    SysMenuService sysMenuService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        // 获取请求地址URL
        String requestUrl = ((FilterInvocation) o).getRequestUrl();

        // 获取DB中的所有菜单信息（菜单信息包括 菜单对应的可访问URL）
        // 特别说明: 菜单表中的菜单url字段【menuUrl】值可能为/employee/**，也可能为/employee/basic/**，
        //          为了优先匹配/employee/basic/**，所以将菜单url字段作为排序key，进行倒序排列，获取菜单信息。
        List<SysMenu> allMenu = sysMenuService.findAllOrderByMenuUrlDesc();
        for (SysMenu menu : allMenu) {
            // 如果 DB中菜单项对应的可访问URL 和 请求地址的URL一致，并且该菜单对应的角色存在的话，将该角色存储。（请求地址URL对应的角色）
            if (antPathMatcher.match(menu.getMenuUrl(), requestUrl) && menu.getSysRoles().size() > 0) {
                Set<SysRole> roles = menu.getSysRoles();
                String[] values = new String[roles.size()];
                int cnt = 0;
                for (SysRole sysRole : roles) {
                    values[cnt] = sysRole.getRoleName();
                    cnt++;
                }
                return SecurityConfig.createList(values);
            }
        }
        // 没有匹配上的资源，认为是没有权限。返回值设定一个特殊的角色值。（注意：此处不能设置空或者空字符串）
        return SecurityConfig.createList("ROLE_NOT_EXIST");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
