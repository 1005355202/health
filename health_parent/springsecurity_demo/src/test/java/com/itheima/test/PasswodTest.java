package com.itheima.test;

import com.itheima.pojo.User;
import com.itheima.utils.MD5Utils;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

/**
 * @Description:
 * @Author: yp
 */
public class PasswodTest {
    @Test
    //MD5 相同的密码 加密之后的是同一个值
    public void fun01(){
        //1.注册(绑定就是一个User)
        User user = new User(new Date(), "男", "zs", "123456", "18824567890");
        User user02 = new User(new Date(), "女", "ls", "123456", "18824561110");
        //2.加密
        user.setPassword(MD5Utils.md5(user.getPassword()));
        //3.再存到数据库
        System.out.println(user.getPassword()); //e10adc3949ba59abbe56e057f20f883e
    }

    @Test
    //MD5+加盐(特殊内容)
    public void fun02(){
        //1.注册(绑定就是一个User)
        User user = new User(new Date(), "女", "ls", "123456", "1394567890");
        //2.加密
        String telephone = user.getTelephone();
        String password = user.getPassword();
        String gender = user.getGender();
        String username = user.getUsername();
        password =   MD5Utils.md5(username+password+telephone+gender);
        user.setPassword(password);
        //3.再存到数据库
        System.out.println(user.getPassword()); //253f13c01c6af32e7d445768ebe75a21 , 126f33c01e35a9fb20502d8ba9c1d1a4
    }

    @Test
    public void fun03(){
        //1.登录(用户名,密码)
        //先根据用户名查询User
        User user = new User(new Date(), "女", "ls", "126f33c01e35a9fb20502d8ba9c1d1a4", "1394567890");

        //2.取出telephone,gender, username, 自己提交的密码进行加密
        String telephone = user.getTelephone();
        String password ="123456";
        String gender = user.getGender();
        String username = user.getUsername();
        password =   MD5Utils.md5(username+password+telephone+gender);

        //3.把password和数据库里面比对



    }


    @Test
    public void fun04(){
        //1.创建Bcryt对象
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //2.加密
        //String password =   encoder.encode("123456");
        //System.out.println(password);

        //$2a$10$o7u8nFRt37rk4v0b9UhJI.Ctx2ra7NaC64dIYAzUcHqeLO23I4qEK
        //$2a$10$wQdfxk94taOxlJNkbQm50uq/mscsHu6ZMzYwwbUOSlJebKPCs9Zzi
        //$2a$10$zX3nhrAnubrKMC7DNnLn9eY4QnklbfSWt/IQ0mSfoSDF95cvVx8CK

        //3.验证
        System.out.println(encoder.matches("123456", "$2a$10$o7u8nFRt37rk4v0b9UhJI.Ctx2ra7NaC64dIYAzUcHqeLO23I4qEK"));
        System.out.println(encoder.matches("123456", "$2a$10$wQdfxk94taOxlJNkbQm50uq/mscsHu6ZMzYwwbUOSlJebKPCs9Zzi"));
        System.out.println(encoder.matches("123456", "$2a$10$zX3nhrAnubrKMC7DNnLn9eY4QnklbfSWt/IQ0mSfoSDF95cvVx8CK"));
        System.out.println(encoder.matches("123457", "$2a$10$zX3nhrAnubrKMC7DNnLn9eY4QnklbfSWt/IQ0mSfoSDF95cvVx8CK"));

    }

}
