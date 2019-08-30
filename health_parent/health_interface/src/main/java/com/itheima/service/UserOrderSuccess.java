package com.itheima.service;

import com.itheima.pojo.QueryOrder;

import java.util.List;
import java.util.Map;

public interface UserOrderSuccess {
    //查询一个手机号的所有预约信息
    List<QueryOrder> queryOrder(String username);
    //取消单个预约
    void cancel(QueryOrder queryOrder);
    //修改预约时间
    void alter(QueryOrder queryOrder, String time);

    Map<String, List<String>> sevenDays();

}
