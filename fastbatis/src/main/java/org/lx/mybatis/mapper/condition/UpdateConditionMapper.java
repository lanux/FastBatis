package org.lx.mybatis.mapper.condition;

import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.entity.Condition;

@FastMapper
public interface UpdateConditionMapper<T> {
    int updateByCondition(T t, Condition condition);

    int updateSelectiveByCondition(T t, Condition condition);

}
