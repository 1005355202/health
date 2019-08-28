package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Description: 自定义的认证授权类
 * @Author: yp
 */
//@Component:在Spring容器里面默认的id是springSecurityUserService
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据username查询User
        User user =  userService.findByUsername(username);
        //2.判断User是否存在
        if(user == null){
            return null;
        }
        //3.获得user里面的角色集合
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
        Set<Role> roles = user.getRoles();
        if(roles != null && roles.size()>0){
            //4.遍历角色信息, 获得Permission集合
            for (Role role : roles) {
                //5.遍历Permission集合
                Set<Permission> permissions = role.getPermissions();
                if(permissions != null && permissions.size()>0){
                    for (Permission permission : permissions) {
                        grantedAuthorityList.add(new SimpleGrantedAuthority(permission.getKeyword()));
                    }
                }
            }
        }
        //6.进行认证和授权
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username,user.getPassword(),grantedAuthorityList);
        return userDetails;
    }
}
