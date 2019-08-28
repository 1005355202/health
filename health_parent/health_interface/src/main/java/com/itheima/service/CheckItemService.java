package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @Description:
 * @Author: yp
 */
public interface CheckItemService {

    /**
     * 新增CheckItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询CheckItem
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 删除CheckItem
     * @param checkItemId
     */
    void delete(Integer checkItemId);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    CheckItem findById(Integer id);

    /**
     * 更新检查项
     * @param checkItem
     */
    void edit(CheckItem checkItem);

    /**
     * 查询所有的检查项
     * @return
     */
    List<CheckItem> findAll();
}
