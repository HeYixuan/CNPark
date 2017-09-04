package org.springframe.system.dao;



import org.springframe.core.BaseDao;
import org.springframe.domain.system.SystemRole;

import java.util.List;

/**
 * @author: HeYixuan
 * @create: 2017-05-15 14:30
 */
public interface SystemRoleDao extends BaseDao<SystemRole> {

    /**
     * 根据用户ID查询用户所拥有的角色
     * @param params
     * @return
     */
    public List<SystemRole> getRoles(Object[] params);


    /**
     * 查询所有的角色
     * @return
     */
    public List<SystemRole> getList();
}
