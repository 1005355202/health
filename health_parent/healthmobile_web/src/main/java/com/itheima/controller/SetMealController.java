package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author: yp
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;

    /**
     * 根据套餐id查询套餐(包含检查组,检查项)
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal = setMealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 查询所有的套餐
     * @return
     */
    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        try {
           List<Setmeal> list =  setMealService.getSetmeal();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }


}
