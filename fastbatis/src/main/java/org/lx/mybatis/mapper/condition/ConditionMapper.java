package org.lx.mybatis.mapper.condition;

import org.lx.mybatis.annotation.FastMapper;

@FastMapper
public interface ConditionMapper<T> extends DeleteConditionMapper<T>, UpdateConditionMapper<T>, SelectConditionMapper<T> {
}
