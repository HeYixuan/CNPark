package org.springframe.security;


import org.springframe.domain.system.SystemRole;
import org.springframe.domain.system.SystemUser;
import org.springframe.system.dao.SystemUserDao;
import org.springframe.system.service.SystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by HeYixuan on 2017/4/17.
 */
@Service
public class SpringSecurityService implements UserDetailsService {

    @Autowired
    private SystemUserDao systemUserDao;
    @Autowired
    private SystemRoleService systemRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = systemUserDao.loadByUsername(username);
        if (StringUtils.isEmpty(systemUser)){
            throw new UsernameNotFoundException("用户账号 " + username + " 不存在");
        }

        //这个方法里实现了根据用户查询用户所有的角色
        List<SystemRole> roles = systemRoleService.getRoles(username);
        //定义权限集合
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        });
        //如果数据库存的明文加密密码
        //return new User(systemUser.getUsername(),passwordEncoder.encode(systemUser.getPassword()),true,true,true, true, authorities);
        //return new User(systemUser.getUsername(),systemUser.getPassword(),true,true,true, true, authorities);
        return new User(username, systemUser.getPassword(),
                systemUser.isEnabled(),
                systemUser.isAccountNonExpired(),
                systemUser.isCredentialsNonExpired(),
                systemUser.isAccountNonLocked(),
                authorities) {
        };
    }
}
