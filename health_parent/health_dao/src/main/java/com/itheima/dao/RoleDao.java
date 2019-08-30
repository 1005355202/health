package com.itheima.dao;

import com.itheima.pojo.Menu;
import com.itheima.pojo.Role;

import java.util.LinkedHashSet;
import java.util.List;
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


    List<Role> findAll();

    LinkedHashSet<Menu> findMenusByRoleId(Integer roleId);

    Role findByRoleId(Integer id);
}
