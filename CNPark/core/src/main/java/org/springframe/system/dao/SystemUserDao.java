package org.springframe.system.dao;


import org.springframe.core.BaseDao;
import org.springframe.domain.system.SystemUser;
import org.springframe.util.Page;

/**
 * @author: HeYixuan
 * @create: 2017-05-15 14:29
 */
public interface SystemUserDao extends BaseDao<SystemUser> {

    /**
     * 查询所有用户分页
     * @return
     */
    public Page<SystemUser> getList(Object[] obj, int pageNo, int pageSize);

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    SystemUser loadByUsername(String username);

    /**
     * 登陆失败,修改登陆次数+1
     * @param username
     */
    void updateAttempts(String username);

    /**
     * 登陆成功,重置登陆次数0
     * @param username
     */
    void resetAttempts(String username);

    /**
     * 检查用户是否存在
     * @param username
     * @return
     */
    boolean isExists(String username);

    /**
     * 锁定账户
     * @param username
     */
    void lock(String username, boolean bool);
}
