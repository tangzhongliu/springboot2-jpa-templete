package com.lemon.templete.service.impl;

import com.lemon.templete.enums.ErrorInfoEnum;
import com.lemon.templete.enums.QueryTypeEnum;
import com.lemon.templete.domain.BaseDomain;
import com.lemon.templete.exception.BusinessException;
import com.lemon.templete.repository.BaseRepository;
import com.lemon.templete.repository.specification.SpecificationFactory;
import com.lemon.templete.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.Map.Entry;

/**
 * 描述: 服务层实现类基类
 * @author 汤中流
 * @date 2019/10/25
 *
 */
@SuppressWarnings("unchecked")
public abstract class BaseServiceImpl<T extends BaseDomain> implements BaseService<T> {

    public abstract BaseRepository<T> getRepository();

    /**
     * 新增数据
     * @param t domain实体
     */
    @Override
    @Transactional
    public void add(T t) {
        getRepository().save(t);
    }

    /**
     * 编辑数据
     * @param id 主键
     * @param t domain实体
     */
    @Override
    @Transactional
    public void modify(Integer id, T t) {
        // 获取操作对象
        T target = getTargetObject(id);
        // 将传入的编辑属性值 设定到 编辑对象
        BeanUtils.copyProperties(t, target, getIgnoreProperties(t));
        getRepository().save(target);
    }

    /**
     * 编辑数据状态
     * @param id 主键
     * @param status 数据状态（0:无效  1:有效）
     */
    @Override
    @Transactional
    public void modifyStatus(Integer id, Integer status) {
        // 获取操作对象
        T target = getTargetObject(id);
        // 设定【状态】
        target.setStatus(status);
        getRepository().save(target);
    }

    /**
     * 删除数据
     * @param id 主键
     */
    @Override
    @Transactional
    public void delete(Integer id) {
        // 获取操作对象
        T target = getTargetObject(id);
        getRepository().deleteById(id);
    }

    /**
     * 获取所有数据信息
     * @return 所有数据信息
     */
    @Override
    public List<T> findAll() {
        List<T> resultList = getRepository().findAll();
        checkResultObject(resultList);
        return resultList;
    }

    /**
     * 获取所有（分页）数据信息
     * @param pageable 分页条件
     * @return 所有数据的分页信息
     */
    @Override
    public Page<T> findAll(Pageable pageable) {
        Page<T> resultPage = getRepository().findAll(pageable);
        checkResultObject(resultPage);
        return resultPage;
    }

    /**
     * 获取指定ID对应的数据信息
     * @param id 数据主键
     * @return 数据信息
     */
    @Override
    public T findById(Integer id) {
        T t = getRepository().findById(id).orElse(null);
        checkResultObject(t);
        return t;
    }

    /**
     * 获取满足查询条件的数据信息
     * （多个条件间是and关系）
     * @param param 查询条件Map<key, value>,
     *              key的格式为[实体内属性名:过滤方式],过滤方式请见QueryTypeEnum.java,
     *              例如：key="username:LIKE", value="test"
     * @return 满足查询条件的数据信息
     */
    @Override
    public List<T> findByConditionAnd(Map<String, Object> param) {
        // 查询条件生成
        Specification specification = createSpecification(param);
        // 查询
        return getRepository().findAll(specification);
    }

    /**
     * 获取满足查询条件的(排序)数据信息
     * （多个条件间是and关系）
     * @param param 查询条件Map<key, value>,
     *              key的格式为[实体内属性名:过滤方式],过滤方式请见QueryTypeEnum.java,
     *              例如：key="username:LIKE", value="test"
     * @param sort 排序
     * @return 满足查询条件的数据信息
     */
    @Override
    public List<T> findByConditionAnd(Map<String, Object> param, Sort sort) {
        // 查询条件生成
        Specification specification = createSpecification(param);
        // 查询
        return getRepository().findAll(specification, sort);
    }

    /**
     * 获取满足查询条件的（分页）数据信息
     * （多个条件间是and关系）
     * @param param 查询条件Map<key, value>,
     *              key的格式为[实体内属性名:过滤方式],过滤方式请见QueryTypeEnum.java,
     *              例如：key="username:LIKE", value="test"
     * @param pageable 分页条件
     * @return 满足查询条件的（分页）数据信息
     */
    @Override
    public Page<T> findByConditionAnd(Map<String, Object> param, Pageable pageable) {
        // 查询条件生成
        Specification specification = createSpecification(param);
        // 查询
        return getRepository().findAll(specification, pageable);
    }

    // region ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 以下是私有方法 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    /**
     * 根据ID获取实体对象
     * （用于更新和删除操作前，确认操作对象是否存在）
     * @param id
     * @return 实体对象
     */
    private T getTargetObject(Integer id) {
        // 根据传入的主键ID，获取操作对象
        Optional<T> targetOptional = getRepository().findById(id);
        if (!targetOptional.isPresent()) {
            throw new BusinessException(ErrorInfoEnum.TARGET_NOT_FOUND);
        }
        return targetOptional.get();
    }

    /**
     * 检验查询结果
     * 未取得结果的抛出业务异常
     * @param object 查询结果
     */
    private void checkResultObject(Object object) {
        if (object instanceof List) {
            if (object == null || ((List) object).size() == 0) {
                throw new BusinessException(ErrorInfoEnum.RESULT_NOT_FOUND);
            }
        } else if (object instanceof Page) {
            if (((Page) object).getTotalElements() == 0) {
                throw new BusinessException(ErrorInfoEnum.RESULT_NOT_FOUND);
            }
        } else {
            if (object == null) {
                throw new BusinessException(ErrorInfoEnum.RESULT_NOT_FOUND);
            }
        }
    }

    /**
     * 获取传入对象中 值为空的属性数组
     * @param source
     * @return
     */
    private String[] getIgnoreProperties(Object source) {
        final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();

        Set<String> ignoreProperties = new HashSet<String>();
        for(PropertyDescriptor pd : pds) {
            Object propertyValue = beanWrapper.getPropertyValue(pd.getName());
            if (propertyValue == null
                    || (propertyValue instanceof Set && ((Set) propertyValue).size() == 0)) {
                ignoreProperties.add(pd.getName());
            }
        }
        String[] result = new String[ignoreProperties.size()];
        return ignoreProperties.toArray(result);
    }

    /**
     * 根据查询属性，查询类型和属性值，组合查询语句
     * @param attribute 查询属性
     * @param queryType 查询类型
     * @param value 属性值
     * @return 查询条件语句
     */
    private Specification getSpecification(String attribute, String queryType, Object value) {
        if (QueryTypeEnum.EQ.name().equals(queryType)) {
            return SpecificationFactory.equal(attribute, value);
        }
        if (QueryTypeEnum.NE.name().equals(queryType)) {
            return SpecificationFactory.notEqual(attribute, value);
        }
        if (QueryTypeEnum.LIKE.name().equals(queryType)) {
            return SpecificationFactory.containsLike(attribute, String.valueOf(value));
        }
        if (QueryTypeEnum.START.name().equals(queryType)) {
            return SpecificationFactory.startWithLike(attribute, String.valueOf(value));
        }
        if (QueryTypeEnum.END.name().equals(queryType)) {
            return SpecificationFactory.endWithLike(attribute, String.valueOf(value));
        }
        if (QueryTypeEnum.LT.name().equals(queryType)) {
            return SpecificationFactory.lt(attribute, value);
        }
        if (QueryTypeEnum.LE.name().equals(queryType)) {
            return SpecificationFactory.le(attribute, value);
        }
        if (QueryTypeEnum.GT.name().equals(queryType)) {
            return SpecificationFactory.gt(attribute, value);
        }
        if (QueryTypeEnum.GE.name().equals(queryType)) {
            return SpecificationFactory.ge(attribute, value);
        }
        return null;
    }

    /**
     * 生成查询条件语句
     * （多个条件间是and关系）
     * @param param 查询条件Map<key, value>,
     *              key的格式为[实体内属性名:过滤方式],过滤方式请见QueryTypeEnum.java,
     *              例如：key="username:LIKE", value="test"
     * @return
     */
    private Specification createSpecification(Map<String, Object> param) {
        // 查询条件准备
        List<Specification> specWhereList = new ArrayList<Specification>();
        for (Entry<String, Object> entry : param.entrySet()) {
            Object value = entry.getValue();
            if (value == null || StringUtils.isBlank(value.toString())) {
                continue;
            }
            String key = entry.getKey();
            String[] keyArray = key.split(":");
            specWhereList.add(getSpecification(keyArray[0], keyArray[1], value));
        }

        // 查询条件生成
        Specification specification = SpecificationFactory.greaterThan("id", 0);
        for (Specification specWhere : specWhereList) {
            specification = specification.and(specWhere);
        }
        return specification;
    }
    // endregion
}
