package com.heima.utils.threadlocal;

import com.heima.model.user.pojos.ApUser;
import com.heima.model.wemedia.pojos.WmUser;

/**
 * TODO
 *
 * @author QLP
 * @version 1.0
 * @date 2021/9/5 12:21
 */
public class AppThreadLocalUtils {

    private final  static ThreadLocal<ApUser> userThreadLocal = new ThreadLocal<>();

    /**
     * 设置当前线程中的用户
     * @param user
     */
    public static void setUser(ApUser user){
        userThreadLocal.set(user);
    }

    /**
     * 获取线程中的用户
     * @return
     */
    public static ApUser getUser( ){
        return userThreadLocal.get();
    }
}
