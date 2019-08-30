package com.itheima.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSuccessdao;
import com.itheima.pojo.QueryOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = UserOrderSuccess.class)
public class UserOrderSuccessimpl implements UserOrderSuccess {
    @Autowired
    private OrderSuccessdao dao;
    @Override
    public List<QueryOrder> queryOrder(String username) {
            return    dao.queryOrder(username);
        }

     //取消预约方法 ;
    @Transactional
    @Override
    public void cancel(QueryOrder queryOrder) {
      int cancel=dao.querycancel(queryOrder);
        dao.cancel(cancel);
        dao.cancelOrdersetting(queryOrder);
    }
    //修改预约时间的方法...
    @Override
    public void alter(QueryOrder queryOrder, String time) {
       //先判断当前时间可不可以预约...
       int count= dao.queryTime(time);
       if(count!=1){ throw  new RuntimeException();}
        //可以预约修改预约时间...
        String orderDate = queryOrder.getOrderDate();
        Map map=new HashMap();
        map.put("old",orderDate);
        map.put("time",time);
        dao.modify(map);
        //修改后 ,更新预约人数
        dao.updateOld(orderDate);
        dao.updateTime(time);

    }
    //查询最近一周预约人数,
    @Override
    public Map<String, List<String>> sevenDays() {
        Format f = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, 8);// 今天过后的一周,,,
        Date tomorrow = c.getTime();
        //f.format(today)
        Map map=new HashMap();
        map.put("today",f.format(today));
        map.put("tomorrow",f.format(tomorrow));
        List<String> listTime= dao.sevenDaysTime(map);
        List<String> listCount= dao.sevenDaysCount(map);
        Map map1=new HashMap();
        map1.put("today",listTime);
        map1.put("tomorrow",listCount);
        return map1;

    }

}

