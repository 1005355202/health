package com.itheima.dao;

import com.itheima.pojo.User;

/**
 * @Description:
 * @Author: yp
 */
public interface UserDao {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);
}
