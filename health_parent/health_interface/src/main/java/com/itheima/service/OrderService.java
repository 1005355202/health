package com.itheima.service;

import com.itheima.entity.Result;

import java.util.List;
import java.util.Map; /**
 * @Description:
 * @Author: yp
 */
public interface OrderService {

    /**
     * 提交预约
     * @param map
     * @return
     */
    Result submit(Map<String, Object> map) throws Exception;

    /**
     * 根据预约id 查询预约成功详情
     * @param id
     * @return
     */
    Map findById(Integer id);

    /**
     * 预约套餐统计
     * @return
     */
    List<Map> getSetmealReport();
}
