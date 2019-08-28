package com.itheima.controller;

import com.itheima.constants.MessageConstant;
import com.itheima.entity.Result;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: yp
 */

@RestController
@RequestMapping("/user")
public class UserController {

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


}
