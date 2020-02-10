package com.lemon.templete.service;

import com.lemon.templete.domain.BaseDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

/**
 * 描述: 服务层基类接口
 * @author 汤中流
 * @date 2019/10/25
 *
 */
public interface BaseService<T extends BaseDomain> {
    /**
     * 新增数据
     * @param t domain实体
     */
    void add(T t);

    /**
     * 编辑数据
     * @param id 主键
     * @param t domain实体
     */
    void modify(Integer id, T t);

    /**
     * 编辑数据状态
     * @param id 主键
     * @param status 数据状态（0:无效  1:有效）
     */
    void modifyStatus(Integer id, Integer status);

    /**
     * 删除数据
     * @param id 主键
     */
    void delete(Integer id);

    /**
     * 获取所有数据信息
     * @return 所有数据信息
     */
    List<T> findAll();

    /**
     * 获取所有（分页）数据信息
     * @param pageable 分页条件
     * @return 所有数据的分页信息
     */
    Page<T> findAll(Pageable pageable);

    /**
     * 获取指定ID对应的数据信息
     * @param id 数据主键
     * @return 数据信息
     */
    T findById(Integer id);

    /**
     * 获取满足查询条件的数据信息
     * （多个条件间是and关系）
     * @param param 查询条件Map<key, value>,
     *              key的格式为[实体内属性名:过滤方式],过滤方式请见QueryTypeEnum.java,
     *              例如：key="username:LIKE", value="test"
     * @return 满足查询条件的数据信息
     */
    List<T> findByConditionAnd(Map<String, Object> param);

    /**
     * 获取满足查询条件的（排序）数据信息
     * （多个条件间是and关系）
     * @param param 查询条件Map<key, value>,
     *              key的格式为[实体内属性名:过滤方式],过滤方式请见QueryTypeEnum.java,
     *              例如：key="username:LIKE", value="test"
     * @param sort 排序
     * @return 满足查询条件的数据信息
     */
    List<T> findByConditionAnd(Map<String, Object> param, Sort sort);

    /**
     * 获取满足查询条件的（分页）数据信息
     * （多个条件间是and关系）
     * @param param 查询条件Map<key, value>,
     *              key的格式为[实体内属性名:过滤方式],过滤方式请见QueryTypeEnum.java,
     *              例如：key="username:LIKE", value="test"
     * @param pageable 分页条件
     * @return 满足查询条件的（分页）数据信息
     */
    Page<T> findByConditionAnd(Map<String, Object> param, Pageable pageable);
}
