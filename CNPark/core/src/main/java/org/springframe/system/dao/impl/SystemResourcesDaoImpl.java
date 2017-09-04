package org.springframe.system.dao.impl;

import org.springframe.core.BaseDaoImpl;
import org.springframe.domain.system.SystemResources;
import org.springframe.system.dao.SystemResourcesDao;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author: HeYixuan
 * @create: 2017-05-15 14:42
 */
@Repository
public class SystemResourcesDaoImpl extends BaseDaoImpl<SystemResources> implements SystemResourcesDao {

    @Override
    public Collection<SystemResources> loadByRole(Integer role) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT S.* FROM SYSTEM_RESOURCES S,SYSTEM_ROLE_RESOURCE RS ");
        builder.append("WHERE RS.SYSTEM_RESOURCE_ID = S.ID ");
        builder.append("AND RS.SYSTEM_ROLE_ID = ?;");
        return this.getList(builder.toString(), new Object[]{role});
    }

    @Override
    public Collection<SystemResources> getListByParentId(Object[] params) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT T2.* FROM SYSTEM_RESOURCES T1 ");
        builder.append("LEFT JOIN SYSTEM_RESOURCES AS T2 ");
        builder.append("ON T2.PARENT_ID = T1.ID ");
        builder.append("WHERE T1.ID= ?;");
        return this.getList(builder.toString(), params);
    }

    @Override
    public Collection<SystemResources> getList() {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM SYSTEM_RESOURCES R;");
        return this.getList(builder.toString());
    }

    public Collection<SystemResources> getUnBingList(){
        String sql = "SELECT * FROM SYSTEM_RESOURCES WHERE ID NOT IN (SELECT DISTINCT SYSTEM_RESOURCE_ID FROM SYSTEM_ROLE_RESOURCE);";
        return this.getList(sql);
    }

}
