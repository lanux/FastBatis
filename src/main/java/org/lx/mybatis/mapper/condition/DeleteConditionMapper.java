package org.lx.mybatis.mapper.condition;

import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.entity.Condition;

@FastMapper
public interface DeleteConditionMapper<T> {

    int deleteByCondition(Condition condition);
}
