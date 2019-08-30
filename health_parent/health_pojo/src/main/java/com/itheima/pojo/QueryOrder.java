package com.itheima.pojo;

import java.io.Serializable;

//一个用于封装所有预约信息的Bean
public class QueryOrder implements Serializable {
 private String member;
 private String setmeal;
 private String orderDate;
 private String orderType;
//                             <p>体检人：{{orderInfo.member}}</p>
//                            <p>体检套餐：{{orderInfo.setmeal}}</p>
//                            <p>体检日期：{{orderInfo.orderDate}}</p>
//                            <p>预约类型：{{orderInfo.orderType}}</p>

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getSetmeal() {
        return setmeal;
    }

    public void setSetmeal(String setmeal) {
        this.setmeal = setmeal;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public QueryOrder(String member, String setmeal, String orderDate, String orderType) {
        this.member = member;
        this.setmeal = setmeal;
        this.orderDate = orderDate;
        this.orderType = orderType;
    }

    public QueryOrder() {
    }


}

