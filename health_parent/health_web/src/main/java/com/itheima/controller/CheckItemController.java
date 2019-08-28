package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author: yp
 */
@RestController
@RequestMapping("/checkItem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;



    /**
     * 查询所有的检查项
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<CheckItem> list =  checkItemService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 更新检查项
     * @param checkItem
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.edit(checkItem);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            checkItemService.delete(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }


    /**
     * 分页查询checkItem
     *
     * @param queryPageBean
     * @return
     */
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkItemService.findPage(queryPageBean);
        return pageResult;
    }

    /**
     * 新增checkItem
     *
     * @param checkItem
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            //1.参数绑定
            //2.调用业务, 进行新增
            checkItemService.add(checkItem);
            //3.响应
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

}
