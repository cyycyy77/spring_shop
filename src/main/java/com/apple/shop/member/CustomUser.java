package com.apple.shop.member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {
    public String displayName;
    public Long id; //유저 아이디
    public CustomUser(String username,
                        String password,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
