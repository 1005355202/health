package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.util.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @Description:
 * @Author: yp
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;


    /**
     * 根据日期更新最大预约人数
     * @param orderSetting
     * @return
     */
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody  OrderSetting orderSetting){
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true, MessageConstant.EDIT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_ORDERSETTING_FAIL);
        }
    }


    /**
     * 查询当前月份的预约设置
     * @param date
     * @return
     */
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        try {
            //1.绑定请求参数(2019-08)
            //2.调用业务 获得当前月的预约设置 List<OrderSetting> list
            List<OrderSetting> list = orderSettingService.getOrderSettingByMonth(date);
            //方式一: List<OrderSetting> list  转成 List<Map> list
            /*List<Map> mapList = new ArrayList<Map>();
            for (OrderSetting orderSetting : list) {
                //一个orderSetting 就封装一个Map
                Map map = new HashMap();
                map.put("date",orderSetting.getOrderDate().getDate());
                map.put("number",orderSetting.getNumber());
                map.put("reservations",orderSetting.getReservations());
                mapList.add(map);
            }*/

            //方式二: 在OrderSetting里面定义getDate()方法

            //3.响应
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    /**
     * 导入预约设置
     *
     * @param excelFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            //1.使用工具类解析上传文件 获得List<String[]> list
            List<String[]> list = POIUtils.readExcel(excelFile);
            //2.把List<String[]> 封装成List<OrderSetting>
            List<OrderSetting> orderSettingList = new ArrayList<OrderSetting>();
            if(list != null && list.size()>0){
                for (String[] array : list) {
                    //每遍历一次 就是一个String[] , 就封装成一个OrderSetting对象
                    OrderSetting orderSetting = new OrderSetting(new Date(array[0]), Integer.parseInt(array[1]));
                    orderSettingList.add(orderSetting);
                }
            }
            //3.调用业务, 进行增加
            orderSettingService.add(orderSettingList);
            //4.响应
            return new Result(true, MessageConstant.UPLOAD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.UPLOAD_FAIL);
        }
    }


}
