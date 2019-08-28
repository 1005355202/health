package com.itheima.controller;

import com.itheima.constants.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.util.SMSUtils;
import com.itheima.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @Description:
 * @Author: yp
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        try {
           // 1. 获得用户输入的手机号码
           // 2. 生成验证码
            Integer code = ValidateCodeUtils.generateValidateCode(6);
            System.out.println("生成验证码code=" + code);
            // 3. 使用阿里云发送验证码
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
           // 4. 把验证码存到Redis(5分钟)
            jedisPool.getResource().setex("login_"+telephone,60*60,code.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    /**
     * 发送短信
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        try {
            //0. 获得手机号码(参数绑定)
            //1. 生成验证码
            Integer code = ValidateCodeUtils.generateValidateCode(6);
            System.out.println("生成验证码是:" + code);
            //2. 调用阿里云发送短信
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
            //3. 把验证码存到Redis(存5分钟)
            jedisPool.getResource().setex(SMSUtils.VALIDATE_CODE+"_"+telephone,60*5,code.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }


}
