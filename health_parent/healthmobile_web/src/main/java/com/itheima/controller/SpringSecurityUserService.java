package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Member;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.MemberService;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Description: 自定义的认证授权类
 * @Author: yp
 */
//@Component:在Spring容器里面默认的id是springSecurityUserService

public class SpringSecurityUserService implements UserDetailsService {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

//根据手机号码 ,和验证码,权限验证 ;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据username查询User
        Member member =  memberService.findByTelephone(username);
        if(member == null){
        //2 .不是, 自动注册为会员 ,
            member =   new Member();
            member.setPhoneNumber(username);
            member.setRegTime(new Date());
            memberService.add(member);
        }
        String redisCode =  jedisPool.getResource().get("login_"+username);
        System.out.println(redisCode);
        //3.进行认证和授权
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username,"{noop}"+redisCode,grantedAuthorityList);
        return userDetails;
    }
}
