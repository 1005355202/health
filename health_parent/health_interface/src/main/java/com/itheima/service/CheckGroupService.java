package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

/**
 * @Description:
 * @Author: yp
 */
public interface CheckGroupService {

    /**
     * 新增检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    /**
     * 根据检查组的id查询出关联的检查项的id集合
     * @return
     */
    List<Integer> findCheckItemIdsById(Integer id);

    /**
     * 更新
     * @param checkGroup
     * @param checkitemIds
     */
    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 查询所有的检查组
     * @return
     */
    List<CheckGroup> findAll();
}
