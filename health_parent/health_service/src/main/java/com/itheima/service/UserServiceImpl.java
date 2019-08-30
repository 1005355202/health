package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author: yp
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 根据用户名查询
     *
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        //方式一: 调用一次Dao, 直接使用MyBatis的映射文件进行关联
        //方式二: 调用三次Dao (调用UserDao根据用户名查询User, 调用RoleDao查询当前用户的角色, 调用PermissionDao查询当前角色拥有的权限)
        //1.调用UserDao根据用户名查询User
        User user = userDao.findByUsername(username);
        //2.调用RoleDao查询当前用户的角色(当前用户的id作为条件)
        if(user != null){
            Integer userId = user.getId();
            Set<Role> roles = roleDao.findByUserId(userId);
            //3.调用PermissionDao查询当前角色拥有的权限
            if(roles != null && roles.size()>0){
                for (Role role : roles) {
                    //SELECT * FROM t_permission WHERE id in(SELECT permission_id FROM t_role_permission WHERE role_id = ?)
                    Integer roleId = role.getId();
                    Set<Permission> permissions = permissionDao.findByRoleId(roleId);
                    role.setPermissions(permissions);
                }
            }
            user.setRoles(roles);
        }


        return user;
    }

    @Override
    public void add(User user, Integer[] roles) {


        userDao.add(user);

        Integer userId = user.getId();

        setUserAndRole(userId,roles);
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        //传入当前页码和每页显示条数 调用pagehelper
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        //传入搜索条件 返回page 里面封装了分页查询的数据
        Page<User> page = userDao.findByConditions(queryPageBean.getQueryString());

        //封装pageresult对象
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());


        return pageResult;
    }

    /**
     * 查询用户基本信息
     *
     * @param id
     * @return
     */
    @Override
    public User findByUserId(Integer id) {

        return userDao.findByUserId(id);
    }

    /**
     * 查询该用户对应的所有角色id
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findRolesByUserId(Integer id) {
        return userDao.findRolesByUserId(id);
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @param roles
     */
    @Override
    public void edit(User user, Integer[] roles) {

        userDao.edit(user);

        userDao.deleteRoleIdsByUserId(user.getId());


        setUserAndRole(user.getId(),roles);

    }

    /**
     * 删除用户
     *
     * @param
     */
    @Override
    public void delete(Integer userId) {

        userDao.deleteRoleIdsByUserId(userId);


        userDao.deleteUserByUserId(userId);
    }

    /**
     * 查询用户角色集合
     * @param id
     * @return
     */
    @Override
    public Set<Role> findRoleSetByUid(Integer id) {

        return roleDao.findByUserId(id);
    }

    /**
     * 遍历角色id 添加到中间表
     * @param userId
     * @param roles
     */
    private void setUserAndRole(Integer userId, Integer[] roles) {

        if(roles!=null){
            for (Integer roleId : roles) {
                Map map = new HashMap();
                map.put("userId",userId);
                map.put("roleId",roleId);
                userDao.setUserAndRole(map);
            }

        }


    }



}
