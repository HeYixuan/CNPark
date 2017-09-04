package org.springframe.system.dao;


import org.springframe.core.BaseDao;
import org.springframe.domain.system.SystemResources;

import java.util.Collection;

/**
 * @author: HeYixuan
 * @create: 2017-05-15 14:31
 */
public interface SystemResourcesDao extends BaseDao<SystemResources> {

    /**
     * 根据角色查询所有资源
     * @param role
     * @return
     */
    public Collection<SystemResources> loadByRole(Integer role);

    /**
     * 根据父节点查询所有的子节点
     * @param params
     * @return
     */
    public Collection<SystemResources> getListByParentId(Object[] params);

    /**
     * 查询所有资源
     * @return
     */
    public Collection<SystemResources> getList();

    /**
     * 查询所有角色未绑定的资源
     * @return
     */
    public Collection<SystemResources> getUnBingList();

}
