package com.app.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class RedirectUtil {

    public static String getRedirectUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                return "/admin/users";
            } else if (grantedAuthority.getAuthority().equals("ROLE_CUSTOMER")) {
                return "/customer/rooms";
            } else if (grantedAuthority.getAuthority().equals("ROLE_HOTEL_STAFF")) {
                return "/manager/dashboard";
            }
        }
        return null;
    }

}
