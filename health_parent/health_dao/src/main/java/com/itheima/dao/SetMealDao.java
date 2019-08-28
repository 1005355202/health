package com.itheima.dao;

import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map; /**
 * @Description:
 * @Author: yp
 */
public interface SetMealDao {

    /**
     * 新增套餐t_setmeal插入一条记录
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 套餐管理检查组t_setmeal_checkgroup插入多条记录
     * @param map
     */
    void setSetMealAndCheckgroup(Map map);

    /**
     * 查询所有的套餐
     * @return
     */
    List<Setmeal> getSetmeal();

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    Setmeal findById(Integer id);
}
