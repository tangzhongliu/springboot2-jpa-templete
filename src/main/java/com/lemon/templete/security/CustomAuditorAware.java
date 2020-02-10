package com.lemon.templete.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class CustomAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        String currentUserName = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                currentUserName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                currentUserName = (String) authentication.getPrincipal();
            }
        }

        if (currentUserName == null) {
            return Optional.empty();
        } else {
            return Optional.of(currentUserName);
        }
    }
}
