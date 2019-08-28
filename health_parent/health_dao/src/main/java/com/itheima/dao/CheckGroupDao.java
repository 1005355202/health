package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;

import java.util.List;
import java.util.Map;
/**
 * @Description:
 * @Author: yp
 */
public interface CheckGroupDao {

    /**
     * 保存检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 检查组关联检查项
     * @param map
     */
    void setCheckGroupAndCheckItem(Map map);

    /**
     * 分页查询检查组
     * @param queryString
     * @return
     */
    Page<CheckGroup> findByConditions(String queryString);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    /**
     * 根据检查组的id查询出关联的检查项的id集合
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsById(Integer id);

    /**
     * 更新检查组
     * @param checkGroup
     */
    void edit(CheckGroup checkGroup);

    /**
     * 根据检查组Id删除关联的检查项id
     * @param id
     */
    void deleteCheckItemsById(Integer id);

    /**
     * 查询所有的检查组
     * @return
     */
    List<CheckGroup> findAll();
}
