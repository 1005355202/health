package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.RoleService;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author: yp
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;


    @Reference
    private RoleService roleService;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 获得用户信息
     * @return
     */
    @RequestMapping("/getUserInfo")
    public Result getUserInfo(){
        try {
            //SpringSecurity里面获得
            //1.获得Security上下文(环境)对象
            SecurityContext securityContext = SecurityContextHolder.getContext();
            //2.获得User(是SpringSecurity的user)
            org.springframework.security.core.userdetails.User user = (User) securityContext.getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    /**
     * 新增用户
     * @param user
     * @return
     */

    @RequestMapping("/add")
    public Result add(@RequestBody com.itheima.pojo.User user, Integer[] roles){

        try {

            if(user.getPassword()!=null &&!"".equals(user.getPassword())){
                String passwordEncode = bCryptPasswordEncoder.encode(user.getPassword());
                user.setPassword(passwordEncode);
            }
            userService.add(user,roles);

            return new Result(true, MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_USER_FAIL);
        }

    }

    /**
     * 分页查询用户
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        PageResult pageResult = userService.findPage(queryPageBean);

        return pageResult;
    }

    /**
     * 查询用户的基本信息
     * @param id
     * @return
     */
    @RequestMapping("/findByUserId")
    public Result findByUserId(Integer id){

        try {

            com.itheima.pojo.User user = userService.findByUserId(id);

            return new Result(true, MessageConstant.QUERY_USER_SUCCESS,user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_USER_FAIL);
        }

    }

    /**
     * 查询该用户对应的所有角色id
     * @param id
     * @return
     */
    @RequestMapping("/findRolesByUserId")
    public Result findRolesByUserId(Integer id){

        try {
            List<Integer> roles = userService.findRolesByUserId(id);

            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,roles);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ROLE_FAIL);
        }

    }

    /**
     * 编辑用户
     * @param user
     * @param roles
     * @return
     */

    @RequestMapping("/edit")
    public Result edit(@RequestBody com.itheima.pojo.User user,Integer[] roles){

        try {

            if(user.getPassword()!=null &&!"".equals(user.getPassword())){
                String passwordEncode = bCryptPasswordEncoder.encode(user.getPassword());
                user.setPassword(passwordEncode);
            }

            userService.edit(user,roles);
            return new Result(true, MessageConstant.EDIT_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_USER_FAIL);
        }

    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete( Integer id ){

        try {
            userService.delete(id);

            return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
        }catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_USER_FAIL);
        }

    }





}
