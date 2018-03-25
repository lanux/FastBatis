package org.lx.mybatis.mapper.condition;

import org.apache.ibatis.mapping.SqlCommandType;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.annotation.StatementProvider;
import org.lx.mybatis.entity.Condition;
import org.lx.mybatis.provider.DynamicSqlProvider;

@FastMapper
public interface DeleteConditionMapper<T> {

    @StatementProvider(type = DynamicSqlProvider.class,method = "deleteByCondition",commandType = SqlCommandType.DELETE)
    int deleteByCondition(Condition condition);
}
