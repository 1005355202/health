package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.User;

import java.util.List;
import java.util.Map;

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

    /***
     * 添加用户基本信息
     * @param user
     */
    void add(User user);

    /**
     *添加用户对应的角色信息
     * @param map
     */
    void setUserAndRole(Map map);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<User> findByConditions(String queryString);

    /**
     * 查询用户基本信息
     *
     * @param id
     * @return
     */
    User findByUserId(Integer id);


    /**
     * 查询该用户对应的所有角色id
     *
     * @param id
     * @return
     */
    List<Integer> findRolesByUserId(Integer id);

    /**
     * 更新基本信息
     * @param user
     */
    void edit(User user);

    /**
     * 删除之前关联的id
     * @param id
     */
    void deleteRoleIdsByUserId(Integer id);

    /**
     * 删除用户
     * @param userId
     */
    void deleteUserByUserId(Integer userId);

}
