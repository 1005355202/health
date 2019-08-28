package com.itheima.service;

import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: yp
 */
public interface SetMealService {

    /**
     * 新增套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 查询所有的套餐
     * @return
     */
    List<Setmeal> getSetmeal();

    /**
     * 根据套餐id查询套餐(包含检查组,检查项)
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

}
