package com.itheima.jobs;

import com.itheima.constants.RedisConstant;
import com.itheima.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @Description:
 * @Author: yp
 */
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
        //1.求出两个集合Set的差值 获得Set集合
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if(set != null && set.size()>0){
            //2.遍历Set集合
            for (String pic : set) {
                System.out.println("要删除的pic=" + pic);
                //3.删除七牛云里面存的图片
                QiniuUtils.deleteFileFromQiniu(pic);
                //4.删除redis里面存的图片(SETMEAL_PIC_RESOURCES)
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,pic);
            }
        }
    }

}
