package org.springframe.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframe.constans.Constans;
import org.springframe.domain.system.SystemUser;
import org.springframe.system.dao.SystemUserDao;
import org.springframe.system.service.SystemUserService;
import org.springframe.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * @author HeYixuan
 * @create 2017-04-26 21:03
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    private Logger logger =  LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SystemUserDao systemUserDao;
    //private LoadingCache<String, Integer> attemptsCache;

    /**
     * 查询所有用户分页
     * @return
     */
    public Page<SystemUser> getList(Object [] obj, int pageNo, int pageSize){
        try {
            return systemUserDao.getList(obj, pageNo, pageSize);
        } catch (Exception e){
            logger.error("查询所有用户分页方法失败{} ", obj, e.getMessage());
            return null;
        }
    }

    /**
     * 登陆失败,修改登陆次数+1
     * 登陆成功,重置登陆次数 0
     * @param username
     */
    @Override
    public void updateAttempts(String username) {
        try{
            SystemUser systemUser = systemUserDao.loadByUsername(username) ;
            boolean result = systemUserDao.isExists(username) ;
            //如果用户不为空,
            if (!StringUtils.isEmpty(systemUser) && result){
                if (systemUser.getAttempts() + 1 <= 3){
                    //如果次数小于2就加1,不能为3是防止2+1等于3,这里只是修改尝试次数+1没有锁定账户
                    systemUserDao.updateAttempts(username);
                }
                if (systemUser.getAttempts() +1 == Constans.MAX_ATTEMPTS){
                    //如果尝试登陆次数是2+1大于或等于3次就锁定
                    systemUserDao.lock(username, false);
                    // 并且抛出账号锁定异常
                    throw new LockedException("locked");
                    //throw new LockedException("用户账号已被锁定，请联系管理员解锁");
                }
                if (systemUser.getAttempts() +1 > Constans.MAX_ATTEMPTS){
                    throw new LockedException("locked");
                }
            }
        }catch (LockedException e){
            logger.error("用户账号{} 已被锁定，请联系管理员解锁", username, e.getMessage());
        } catch (Exception e){
            logger.error("根据用户名{} 修改登录次数失败.", username, e.getMessage());
        }
    }
}
