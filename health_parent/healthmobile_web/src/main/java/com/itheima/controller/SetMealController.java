package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constants.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @Description:
 * @Author: yp
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private SetMealService setMealService;

    /**
     * 根据套餐id查询套餐(包含检查组,检查项)
     * @param id
     * @return
     */
    //增加了到缓存查询 ,对象存储方式为JSON格式的字符串
    @RequestMapping("/findById")
    public Result findById(Integer id){
        Jedis resource = jedisPool.getResource();
        String byId = resource.get("by"+id);
        if(byId==null){
        try {
            Setmeal setmeal = setMealService.findById(id);
            String str = JSON.toJSONString(setmeal);
            resource.set("by"+id,str);
            resource.close();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            resource.close();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }}else {
            Setmeal setmeal  = JSON.parseObject(byId, Setmeal.class);
            resource.close();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }
    }

    /**
     * 查询所有的套餐
     * @return
     */
    //增加了到缓存查询 ,对象存储方式为JSON格式的字符串
    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        Jedis resource = jedisPool.getResource();
        String setmeal = resource.get("Setmeal");
        if(setmeal==null){
            try {
                List<Setmeal> list =  setMealService.getSetmeal();
                String str = JSON.toJSONString(list);
                resource.set("Setmeal",str);
                resource.close();
                return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,list);
            } catch (Exception e) {
                e.printStackTrace();
                resource.close();
                return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
            }
        }else {
            List<Setmeal> list  = JSON.parseObject(setmeal, List.class);
            resource.close();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,list);
        }

    }


}
