package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.OrderService;
import com.itheima.util.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @Description:
 * @Author: yp
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    /**
     * 根据预约id 查询预约成功详情
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Map map  =  orderService.findById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }

//根据手机号码查询所有预约信息
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map<String,Object> map){
        try {
            //1.获得用户的手机号码和输入的验证码
            String telephone = (String) map.get("telephone");
            String validateCode = (String) map.get("validateCode");
            //2.根据手机号码获得Redis里面存的验证码
            String redisCode = jedisPool.getResource().get(SMSUtils.VALIDATE_CODE + "_" + telephone);
            //3.比较用户输入的验证码和Redis里面存的验证码是否一致
            if(redisCode == null || !redisCode.equals(validateCode)){
                //3.1不一致, 直接响应 验证码验证失败
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
            //3.2一致,  调用业务, 进行预约
            Result result =  orderService.submit(map);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
    }



}
