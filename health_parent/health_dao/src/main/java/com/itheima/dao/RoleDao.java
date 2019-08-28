package com.itheima.dao;

import com.itheima.pojo.Role;

import java.util.Set;

/**
 * @Description:
 * @Author: yp
 */
public interface RoleDao {

    /**
     * 根据userId查询角色(查询当前用户的角色)
     * @param userId
     * @return
     */
    Set<Role> findByUserId(Integer userId);
}
