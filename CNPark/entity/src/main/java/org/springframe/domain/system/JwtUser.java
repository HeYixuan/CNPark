package org.springframe.domain.system;

import org.springframe.core.SerializableModel;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class JwtUser extends SerializableModel {
    /**
     * 用户名
     */
    private String username;

    /**
     * 角色
     */
    private Collection<? extends GrantedAuthority> authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public JwtUser(String username, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    public static JwtUser build(String username, Collection<? extends GrantedAuthority> authorities){
        return new JwtUser(username, authorities);
    }
}
