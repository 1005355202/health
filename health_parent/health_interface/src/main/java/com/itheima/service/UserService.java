package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;


import java.util.List;
import java.util.Set;

public interface UserService {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    User findByUsername(String username);

    void add(User user, Integer[] roles);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 查询用户基本信息
     * @param id
     * @return
     */
    User findByUserId(Integer id);

    /**
     * 查询该用户对应的所有角色id
     * @param id
     * @return
     */
    List<Integer> findRolesByUserId(Integer id);

    /**
     * 修改用户信息
     * @param user
     * @param roles
     */
    void edit(User user, Integer[] roles);

    /**
     * 删除用户
     * @param id
     */
    void delete(Integer id);

    Set<Role> findRoleSetByUid(Integer id);
}
