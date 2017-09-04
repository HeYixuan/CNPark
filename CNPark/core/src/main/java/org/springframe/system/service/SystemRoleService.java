package org.springframe.system.service;


import org.springframe.domain.system.SystemRole;

import java.util.List;

/**
 * Created by HeYixuan on 2017/4/20.
 */
public interface SystemRoleService {

    /**
     * 根据用户名查询用户拥有的角色
     * @return
     */
    public List<SystemRole> getRoles(String username);
}
