package com.lemon.templete.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * specification工厂类
 *
 * @author 汤中流
 * @date 2019/08/29
 */
public final class SpecificationFactory {
    // region------------------------------------ equal ------------------------------------ //
    /**
     * 完全一致查询
     * attribute = value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification equal(String attribute, Object value) {
        return (root, query, cb) -> cb.equal(root.get(attribute), value);
    }

    /**
     * 不一致查询
     * attribute <> value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification notEqual(String attribute, Object value) {
        return (root, query, cb) -> cb.notEqual(root.get(attribute), value);
    }

    /**
     * 完全一致查询-join
     * inner join table where table.attribute = value
     *
     * @param table
     * @param attribute
     * @param value
     * @return
     */
    public static Specification equalJoin(String table, String attribute, Object value) {
        return (root, query, cb) -> cb.equal(root.join(table, JoinType.INNER).get(attribute), value);
    }

    /**
     * 完全一致查询-left join
     * left join table where table.attribute = value
     *
     * @param table
     * @param attribute
     * @param value
     * @return
     */
    public static Specification equalLeftJoin(String table, String attribute, Object value) {
        return (root, query, cb) -> {
            Join<Object, Object> join = root.join(table, JoinType.LEFT);
            return cb.equal(join.get(attribute), value);
//            return join.on(cb.equal(join.get(attribute), value)).getOn();
        };
    }
    // endregion

    // region------------------------------------ in ------------------------------------ //
    /**
     * IN查询
     * attribute in (collection)
     *
     * @param attribute
     * @param collection
     * @return
     */
    public static Specification in(String attribute, Collection collection) {
        return (root, query, cb) -> root.get(attribute).in(collection);
    }
    // endregion

    // region------------------------------------ like ------------------------------------ //
    /**
     * 模糊查询
     * attribute like %value%
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification containsLike(String attribute, String value) {
        return (root, query, cb) -> cb.like(root.get(attribute), "%" + value + "%");
    }

    /**
     * 模糊查询-前方一致
     * attribute like value%
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification startWithLike(String attribute, String value) {
        return (root, query, cb) -> cb.like(root.get(attribute), value + "%");
    }

    /**
     * 模糊查询-后方一致
     * attribute like %value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification endWithLike(String attribute, String value) {
        return (root, query, cb) -> cb.like(root.get(attribute), "%" + value);
    }
    // endregion

    // region------------------------------------ between ------------------------------------ //
    /**
     * 区间查询-Object
     * attribute between min and max
     *
     * @param attribute
     * @param min
     * @param max
     * @return
     */
    public static Specification between(String attribute, Object min, Object max) {
        if (Integer.class == min.getClass()) {
            return between(attribute, (Integer)min, (Integer)max);
        }
        if (Double.class == min.getClass()) {
            return between(attribute, (Double)min, (Double)max);
        }
        if (Long.class == min.getClass()) {
            return between(attribute, (Long)min, (Long)max);
        }
        if (Date.class == min.getClass()) {
            return between(attribute, (Date)min, (Date)max);
        }
        if (Timestamp.class == min.getClass()) {
            return between(attribute, (Timestamp)min, (Timestamp)max);
        }
        return null;
    }

    /**
     * 区间查询-Integer
     * attribute between min and max
     *
     * @param attribute
     * @param min
     * @param max
     * @return
     */
    public static Specification between(String attribute, Integer min, Integer max) {
        return (root, query, cb) -> cb.between(root.get(attribute), min, max);
    }

    /**
     * 区间查询-Double
     * attribute between min and max
     *
     * @param attribute
     * @param min
     * @param max
     * @return
     */
    public static Specification between(String attribute, Double min, Double max) {
        return (root, query, cb) -> cb.between(root.get(attribute), min, max);
    }

    /**
     * 区间查询-Long
     * attribute between min and max
     *
     * @param attribute
     * @param min
     * @param max
     * @return
     */
    public static Specification between(String attribute, Long min, Long max) {
        return (root, query, cb) -> cb.between(root.get(attribute), min, max);
    }

    /**
     * 区间查询-Date
     * attribute between min and max
     *
     * @param attribute
     * @param min
     * @param max
     * @return
     */
    public static Specification between(String attribute, Date min, Date max) {
        return (root, query, cb) -> cb.between(root.get(attribute), min, max);
    }

    /**
     * 区间查询-Timestamp
     * attribute between min and max
     *
     * @param attribute
     * @param min
     * @param max
     * @return
     */
    public static Specification between(String attribute, Timestamp min, Timestamp max) {
        return (root, query, cb) -> cb.between(root.get(attribute), min, max);
    }
    // endregion

    // region------------------------------------ 大于 > ------------------------------------ //
    /**
     * 大于查询-Object
     * attribute > value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification gt(String attribute, Object value) {
        if (Integer.class == value.getClass()) {
            return greaterThan(attribute, (Integer)value);
        }
        if (Double.class == value.getClass()) {
            return greaterThan(attribute, (Double)value);
        }
        if (Long.class == value.getClass()) {
            return greaterThan(attribute, (Long)value);
        }
        if (BigDecimal.class == value.getClass()) {
            return greaterThan(attribute, (BigDecimal)value);
        }
        if (String.class == value.getClass()) {
            return greaterThan(attribute, String.valueOf(value));
        }
        if (Date.class == value.getClass()) {
            return greaterThan(attribute, (Date)value);
        }
        if (Timestamp.class == value.getClass()) {
            return greaterThan(attribute, (Timestamp) value);
        }
        return null;
    }

    /**
     * 大于查询-Integer
     * attribute > value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification greaterThan(String attribute, Integer value) {
        return (root, query, cb) -> cb.greaterThan(root.get(attribute), value);
    }

    /**
     * 大于查询-Double
     * attribute > value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification greaterThan(String attribute, Double value) {
        return (root, query, cb) -> cb.greaterThan(root.get(attribute), value);
    }

    /**
     * 大于查询-Long
     * attribute > value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification greaterThan(String attribute, Long value) {
        return (root, query, cb) -> cb.greaterThan(root.get(attribute), value);
    }

    /**
     * 大于查询-BigDecimal
     * attribute > value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification greaterThan(String attribute, BigDecimal value) {
        return (root, query, cb) -> cb.greaterThan(root.get(attribute), value);
    }

    /**
     * 大于查询-String
     * attribute > value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification greaterThan(String attribute, String value) {
        return (root, query, cb) -> cb.greaterThan(root.get(attribute), value);
    }

    /**
     * 大于查询-Date
     * attribute > value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification greaterThan(String attribute, Date value) {
        return (root, query, cb) -> cb.greaterThan(root.get(attribute), value);
    }

    /**
     * 大于查询-TimeStamp
     * attribute > value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification greaterThan(String attribute, Timestamp value) {
        return (root, query, cb) -> cb.greaterThan(root.get(attribute), value);
    }
    // endregion

    // region------------------------------------ 大于等于 >= ------------------------------------ //
    /**
     * 大于等于查询-Object
     * attribute >= value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification ge(String attribute, Object value) {
        if (Integer.class == value.getClass()) {
            return greaterThanOrEqual(attribute, (Integer)value);
        }
        if (Double.class == value.getClass()) {
            return greaterThanOrEqual(attribute, (Double)value);
        }
        if (Long.class == value.getClass()) {
            return greaterThanOrEqual(attribute, (Long)value);
        }
        if (BigDecimal.class == value.getClass()) {
            return greaterThanOrEqual(attribute, (BigDecimal)value);
        }
        if (String.class == value.getClass()) {
            return greaterThanOrEqual(attribute, String.valueOf(value));
        }
        if (Date.class == value.getClass()) {
            return greaterThanOrEqual(attribute, (Date)value);
        }
        if (Timestamp.class == value.getClass()) {
            return greaterThanOrEqual(attribute, (Timestamp)value);
        }
        return null;
    }

    /**
     * 大于&等于查询-Integer
     * attribute >= value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification greaterThanOrEqual(String attribute, Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attribute), value);
    }

    /**
     * 大于&等于查询-Double
     * attribute >= value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification greaterThanOrEqual(String attribute, Double value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attribute), value);
    }

    /**
     * 大于&等于查询-Long
     * attribute >= value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification greaterThanOrEqual(String attribute, Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attribute), value);
    }

    /**
     * 大于&等于查询-BigDecimal
     * attribute >= value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification greaterThanOrEqual(String attribute, BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attribute), value);
    }

    /**
     * 大于&等于查询-String
     * attribute >= value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification greaterThanOrEqual(String attribute, String value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attribute), value);
    }

    /**
     * 大于&等于查询-Date
     * attribute >= value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification greaterThanOrEqual(String attribute, Date value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attribute), value);
    }

    /**
     * 大于&等于查询-Timestamp
     * attribute >= value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification greaterThanOrEqual(String attribute, Timestamp value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attribute), value);
    }
    // endregion

    // region------------------------------------ 小于 < ------------------------------------ //
    /**
     * 小于查询-Object
     * attribute < value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification lt(String attribute, Object value) {
        if (Integer.class == value.getClass()) {
            return lessThan(attribute, (Integer)value);
        }
        if (Double.class == value.getClass()) {
            return lessThan(attribute, (Double)value);
        }
        if (Long.class == value.getClass()) {
            return lessThan(attribute, (Long)value);
        }
        if (BigDecimal.class == value.getClass()) {
            return lessThan(attribute, (BigDecimal)value);
        }
        if (String.class == value.getClass()) {
            return lessThan(attribute, String.valueOf(value));
        }
        if (Date.class == value.getClass()) {
            return lessThan(attribute, (Date)value);
        }
        if (Timestamp.class == value.getClass()) {
            return lessThan(attribute, (Timestamp)value);
        }
        return null;
    }

    /**
     * 小于查询-Integer
     * attribute < value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification lessThan(String attribute, Integer value) {
        return (root, query, cb) -> cb.lessThan(root.get(attribute), value);
    }

    /**
     * 小于查询-Double
     * attribute < value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification lessThan(String attribute, Double value) {
        return (root, query, cb) -> cb.lessThan(root.get(attribute), value);
    }

    /**
     * 小于查询-Long
     * attribute < value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification lessThan(String attribute, Long value) {
        return (root, query, cb) -> cb.lessThan(root.get(attribute), value);
    }

    /**
     * 小于查询-BigDecimal
     * attribute < value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification lessThan(String attribute, BigDecimal value) {
        return (root, query, cb) -> cb.lessThan(root.get(attribute), value);
    }

    /**
     * 小于查询-String
     * attribute < value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification lessThan(String attribute, String value) {
        return (root, query, cb) -> cb.lessThan(root.get(attribute), value);
    }

    /**
     * 小于查询-Date
     * attribute < value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification lessThan(String attribute, Date value) {
        return (root, query, cb) -> cb.lessThan(root.get(attribute), value);
    }

    /**
     * 小于查询-Timestamp
     * attribute < value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification lessThan(String attribute, Timestamp value) {
        return (root, query, cb) -> cb.lessThan(root.get(attribute), value);
    }
    // endregion

    // region------------------------------------ 小于等于 <= ------------------------------------ //
    /**
     * 小于等于查询-Object
     * attribute <= value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification le(String attribute, Object value) {
        if (Integer.class == value.getClass()) {
            return lessThanOrEqual(attribute, (Integer)value);
        }
        if (Double.class == value.getClass()) {
            return lessThanOrEqual(attribute, (Double)value);
        }
        if (Long.class == value.getClass()) {
            return lessThanOrEqual(attribute, (Long)value);
        }
        if (BigDecimal.class == value.getClass()) {
            return lessThanOrEqual(attribute, (BigDecimal)value);
        }
        if (String.class == value.getClass()) {
            return lessThanOrEqual(attribute, String.valueOf(value));
        }
        if (Date.class == value.getClass()) {
            return lessThanOrEqual(attribute, (Date)value);
        }
        if (Timestamp.class == value.getClass()) {
            return lessThanOrEqual(attribute, (Timestamp) value);
        }
        return null;
    }

    /**
     * 小于&等于查询-Integer
     * attribute <= value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification lessThanOrEqual(String attribute, Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attribute), value);
    }

    /**
     * 小于&等于查询-Double
     * attribute <= value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification lessThanOrEqual(String attribute, Double value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attribute), value);
    }

    /**
     * 小于&等于查询-Long
     * attribute <= value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification lessThanOrEqual(String attribute, Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attribute), value);
    }

    /**
     * 小于&等于查询-BigDecimal
     * attribute <= value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification lessThanOrEqual(String attribute, BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attribute), value);
    }

    /**
     * 小于&等于查询-String
     * attribute <= value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification lessThanOrEqual(String attribute, String value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attribute), value);
    }

    /**
     * 小于&等于查询-Date
     * attribute <= value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification lessThanOrEqual(String attribute, Date value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attribute), value);
    }

    /**
     * 小于&等于查询-Timestamp
     * attribute <= value
     *
     * @param attribute
     * @param value
     * @return
     */
    public static Specification lessThanOrEqual(String attribute, Timestamp value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attribute), value);
    }
    // endregion

}
