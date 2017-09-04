package org.springframe.system.service;


import org.springframe.domain.system.SystemUser;
import org.springframe.util.Page;

/**
 * Created by HeYixuan on 2017/4/26.
 */

public interface SystemUserService {

    /**
     * 查询所有用户分页
     * @return
     */
    public Page<SystemUser> getList(Object[] obj, int pageNo, int pageSize);

    /**
     * 登陆失败,修改登陆次数+1
     * 登陆成功,重置登陆次数 0
     * @param username
     */
    void updateAttempts(String username);
}
