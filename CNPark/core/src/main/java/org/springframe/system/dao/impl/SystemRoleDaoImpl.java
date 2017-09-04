package org.springframe.system.dao.impl;

import org.springframe.core.BaseDaoImpl;
import org.springframe.domain.system.SystemRole;
import org.springframe.system.dao.SystemRoleDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: HeYixuan
 * @create: 2017-05-15 14:41
 */
@Repository
public class SystemRoleDaoImpl extends BaseDaoImpl<SystemRole> implements SystemRoleDao {

    /**
     * 根据用户ID查询用户所拥有的角色
     * @param params
     * @return
     */
    public List<SystemRole> getRoles(Object[] params){
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT R.NAME FROM SYSTEM_ROLE R,SYSTEM_USER_ROLE UR ");
        builder.append("WHERE R.ID = UR.SYSTEM_ROLE_ID ");
        builder.append("AND UR.SYSTEM_USER_ID = ?;");
        return this.getList(builder.toString(), params);
    }

    /**
     * 查询所有的角色
     * @return
     */
    public List<SystemRole> getList(){
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM SYSTEM_ROLE;");
        return this.getList(builder.toString());
    }
}
