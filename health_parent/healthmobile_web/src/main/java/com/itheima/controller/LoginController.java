package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @Description:
 * @Author: yp
 */

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @RequestMapping("/login")
    public Result login(@RequestBody Map map, HttpServletRequest request) {
        try {
            // 1. 获得用户输入的信息(Map)
            // 2. 把用户输入的验证码和redis里面存的验证码进行比较 { "telephone": "15374518821", "validateCode": "123456" }
            String userCode= (String) map.get("validateCode");
            String telephone = (String) map.get("telephone");
            String redisCode =  jedisPool.getResource().get("login_"+telephone);
            if(redisCode == null || !redisCode.equals(userCode)){
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }

            // 3. 如果一致:
            // - 判断是否是会员(根据telephone查询)
            Member member =  memberService.findByTelephone(telephone);
            if(member == null){
                //不是, 自动注册为会员
                member =   new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
            //登录成功, 跳转到首页, 保存用户登录状态(1.CAS 2.签发token)
            request.getSession().setAttribute("member",member);
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.LOGIN_FAIL);
        }
    }


}
