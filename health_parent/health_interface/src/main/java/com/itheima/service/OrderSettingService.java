package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List; /**
 * @Description:
 * @Author: yp
 */
public interface OrderSettingService {

    /**
     * 导入orderSetting
     * @param orderSettingList
     */
    void add(List<OrderSetting> orderSettingList);

    /**
     * 查询当前月份的预约设置
     * @param date
     * @return
     */
    List<OrderSetting> getOrderSettingByMonth(String date);

    /**
     * 更新预约设置
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting);
}
