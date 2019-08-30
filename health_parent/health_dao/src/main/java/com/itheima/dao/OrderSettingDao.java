package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Delete;

import java.util.Date;
import java.util.List;
import java.util.Map;
public interface OrderSettingDao {

    /**
     * 根据日期查询数量
     * @param orderDate
     * @return
     */
    long findCountByOrderDate(Date orderDate);

    /**
     * 更新OrderSetting
     * @param orderSetting
     */
    void editNumberByOrderDate(OrderSetting orderSetting);

    /**
     * 新增OrderSetting
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 查询当前月份的预约设置
     * @param map
     * @return
     */
    List<OrderSetting> getOrderSettingByMonth(Map map);

    @Delete("DELETE FROM t_ordersetting WHERE orderDate <=#{date}")
    void  quDelete(Date date);

    OrderSetting findByOrderDate(Date orderDate);

    void editReservationsByOrderDate(OrderSetting orderSetting);
}
