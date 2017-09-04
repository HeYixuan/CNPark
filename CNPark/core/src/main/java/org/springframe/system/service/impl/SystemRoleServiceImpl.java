package org.springframe.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframe.domain.system.SystemRole;
import org.springframe.domain.system.SystemUser;
import org.springframe.system.dao.SystemRoleDao;
import org.springframe.system.dao.SystemUserDao;
import org.springframe.system.service.SystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by HeYixuan on 2017/4/20.
 */
@Service
@Transactional
public class SystemRoleServiceImpl implements SystemRoleService {

    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemUserDao systemUserDao;

    @Autowired
    private SystemRoleDao systemRoleDao;

    @Override
    public List<SystemRole> getRoles(String username) {
        try{
            SystemUser systemUser = systemUserDao.loadByUsername(username);
            List<SystemRole> roles = systemRoleDao.getRoles(new Object[]{systemUser.getId()});
            return roles;
        }catch (Exception e){
            logger.error("根据用户名{}获取角色信息失败.", username, e.getMessage());
            return null;
        }
    }
}
