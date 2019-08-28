package com.itheima.service;

import com.itheima.pojo.User;

/**
 * @Description:
 * @Author: yp
 */
public interface UserService {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    User findByUsername(String username);
}
