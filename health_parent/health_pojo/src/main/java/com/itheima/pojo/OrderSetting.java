package com.itheima.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约设置【转换json 依赖对象的getXXX()方法, 依赖JavaBean属性】
 */
public class OrderSetting implements Serializable{
    private Integer id ;
    private Date orderDate;//预约设置日期
    private int number;//可预约人数
    private int reservations ;//已预约人数

    public OrderSetting() {
    }

    public OrderSetting(Date orderDate, int number) {
        this.orderDate = orderDate;
        this.number = number;
    }

    //获得日期的日, 返回 最终转成json date:xx
    public int getDate(){
        return orderDate.getDate();
    }

    public Integer getId() {
        return id;
    }  //id

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    } //orderDate

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getNumber() {
        return number;
    } //number

    public void setNumber(int number) {
        this.number = number;
    }

    public int getReservations() {
        return reservations;
    }  //reservations

    public void setReservations(int reservations) {
        this.reservations = reservations;
    }
}
