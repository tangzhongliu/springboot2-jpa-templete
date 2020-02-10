package com.lemon.templete.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication
                     , Object o
                     , Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        // 判断1：如果未登录，抛出异常提示【未登录】(错误信息统一在WebSecurityConfig中处理)
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new BadCredentialsException(null);
        }

        // 获取当前请求URL所需要的角色（权限）列表
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute ca = iterator.next();
            // 当前请求URL所需要的角色（权限）
            String needRole = ca.getAttribute();

            // 获取当前用户所具有的角色（权限）列表
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                // 判断2：如果 当前用户所具有的角色中的任意一个 和 当前请求URL所需要的角色 一致的话，权限验证OK，返回
                // 例如 当前用户所具有角色：A和B，当前请求URL所需要的角色：B和C，那么满足验证条件，返回
                if (authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        // 上边判断1和判断2都不满足的时候，权限验证NG，权限不足，返回(错误信息统一在WebSecurityConfig中处理)
        throw new AccessDeniedException(null);
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
