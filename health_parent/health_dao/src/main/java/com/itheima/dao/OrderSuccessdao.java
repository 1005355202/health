package com.itheima.dao;

import com.itheima.pojo.QueryOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSuccessdao {
     List<QueryOrder> queryOrder(String username) ;

    @Delete("DELETE FROM t_order  WHERE t_order.id=#{a}")
    void cancel(int  cancel);
    @Select("SELECT o.`id` FROM `t_member` m,`t_order` o WHERE m.`id`=o.`member_id` AND m.`name`=#{member} AND o.`orderDate`=#{orderDate}")
    int querycancel(QueryOrder queryOrder);
    @Update("UPDATE t_ordersetting SET reservations=reservations-1 WHERE orderDate=#{orderDate}")
    void cancelOrdersetting(QueryOrder queryOrder);

    @Select("SELECT COUNT(*) FROM `t_ordersetting` WHERE number>reservations AND orderDate=#{a}")
    int queryTime(String time);

    @Update("UPDATE t_order SET orderDate=#{time} WHERE orderDate=#{old}")
    void modify(Map map);
    @Update("UPDATE t_ordersetting SET reservations=reservations-1 WHERE orderDate=#{a}")
    void updateOld(String orderDate);
    @Update("UPDATE t_ordersetting SET reservations=reservations+1 WHERE orderDate=#{a}")
    void updateTime(String time);
    @Select("SELECT orderDate FROM `t_ordersetting` WHERE orderDate>#{today} AND orderDate<#{tomorrow}")
    List<String> sevenDaysTime(Map map);
    @Select("SELECT reservations FROM `t_ordersetting` WHERE orderDate>#{today} AND orderDate<#{tomorrow}")
    List<String> sevenDaysCount(Map map);
}
