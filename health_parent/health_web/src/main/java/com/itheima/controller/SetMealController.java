package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.constants.RedisConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import com.itheima.util.QiniuUtils;
import com.itheima.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

/**
 * @Description:
 * @Author: yp
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;

    @Autowired
    private JedisPool jedisPool;


    /**
     * 文件上传
     * @param imgFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){
        try {
            //1.获得文件名
            String filename = imgFile.getOriginalFilename();
            //2.把文件名改成UUID名字
            filename = UploadUtils.getUUIDName(filename);
            //3.调用七牛云的API进行上传到七牛云【imgFile.getBytes(): 以字节数组方式获得文件的内容】
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),filename);
            //把图片名字存到Redis
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,filename);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,filename);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 新增套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @RequestMapping("/add")
    public Result add( @RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setMealService.add(setmeal,checkgroupIds);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }




}
