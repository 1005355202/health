package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.QueryOrder;
import com.itheima.service.UserOrderSuccess;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: yp
 */

@RestController
@RequestMapping("/user")
public class UserController {
  @Reference
  private UserOrderSuccess service;
    //查询一个手机号所有预约信息的方法 ;
    @RequestMapping("/getUserInfo")
    public Result getUserInfo(){
        try {
            //SpringSecurity里面获得
            //1.获得Security上下文(环境)对象
            SecurityContext securityContext = SecurityContextHolder.getContext();
            //2.获得User(是SpringSecurity的user)
            User user = (User) securityContext.getAuthentication().getPrincipal();
            //3 .查询一个手机号对应的所有预约信息 ,然后更改页面?,不重新写算了,,,
          List<QueryOrder> list= service.queryOrder(user.getUsername());
            System.out.println(list);
            return new Result(true,null,list);
        } catch (Exception e) {
            return new Result(false,null);
        }
    }
    @RequestMapping("/cancel")
    public Result  cancel(@RequestBody QueryOrder queryOrder){
        try{
            service.cancel(queryOrder);
            return new Result(true,"取消预约成功");
        }catch (Exception e){
            return new Result(false,"取消预约失败");
        }

    }
    //修改当前套餐的预约时间
    @RequestMapping("/alter")
    public Result  alter(@RequestBody QueryOrder queryOrder,String time){
        try{
            service.alter(queryOrder,time);
            return new Result(true,"修改预约时间成功");
        }catch (Exception e){
            return new Result(false,"预约失败,更改的时间不可预约..");
        }

    }

    //根据当前日期,查询往后七天的预约人数
    @RequestMapping("/sevenDays")
    public Result sevenDays(){
        try{
         Map<String,List<String>> map= service.sevenDays();
            return new Result(true,"查询预约排行成功",map);
        }catch (Exception e){
            return new Result(false,"查询预约排行失败");
        }



    }



}
