package com.pccw.dubbo.service;

public interface IUserService {
    /**
     * 登录方法
     * @return 是否登录成功
     */
    public boolean login(String username, String password);
}
