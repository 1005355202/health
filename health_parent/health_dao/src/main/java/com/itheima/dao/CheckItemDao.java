package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @Description:
 * @Author: yp
 */
public interface CheckItemDao {

    //保存CheckItem
    void add(CheckItem checkItem);

    //分页条件查询
    Page<CheckItem> findByConditions(String queryString);

    /**
     * 查询当前的检查项是否被引用到了
     * @param checkItemId
     * @return
     */
    long findByCheckItemId(Integer checkItemId);

    /**
     * 根据checkItemId删除检查项
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
     * 查询所有检查项
     * @return
     */
    List<CheckItem> findAll();
}
