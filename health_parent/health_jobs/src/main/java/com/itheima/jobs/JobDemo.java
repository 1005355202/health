package com.itheima.jobs;

import com.itheima.dao.OrderSettingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;




/**
 * @program: sz70_health
 * @description:
 * @author:
 * @create: 2019-08-28 19:53
 **/
@Controller
public class JobDemo {
    @Autowired
    private OrderSettingDao orderSettingDao;


    public void orderSettingDao() {
        orderSettingDao.quDelete(new Date());
        System.out.println("正在清理!");
    }
}

