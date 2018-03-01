package org.lx.mybatis.mapper.condition;

import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.entity.Condition;

import java.util.List;

@FastMapper
public interface SelectConditionMapper<T> {

    List<T> selectByCondition(Condition condition);

    long countByCondition(Condition condition);

}
