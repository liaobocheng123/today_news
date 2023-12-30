package com.heima.utils.thread;

import com.heima.model.user.pojos.ApUser;

public class AppThreadLocalUtil {
    private final static ThreadLocal<ApUser> WM_USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void setUser(ApUser apUser){
        WM_USER_THREAD_LOCAL.set(apUser);
    }
    public static ApUser getUser(){
        return WM_USER_THREAD_LOCAL.get();
    }
    public static void clear(){
        WM_USER_THREAD_LOCAL.remove();
    }

}
