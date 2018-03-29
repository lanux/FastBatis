package org.lx.mybatis.mapper.condition;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.mapping.SqlCommandType;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.annotation.StatementProvider;
import org.lx.mybatis.entity.Condition;
import org.lx.mybatis.provider.DynamicSqlProvider;

import java.util.List;

@FastMapper
public interface SelectConditionMapper<T> {

    @ResultMap("BaseResultMap")
    @StatementProvider(type = DynamicSqlProvider.class, method = "selectByCondition", commandType = SqlCommandType.SELECT)
    List<T> selectByCondition(Condition condition);

    @StatementProvider(type = DynamicSqlProvider.class, method = "countByCondition", commandType = SqlCommandType.SELECT)
    @ResultType(Long.class)
    long countByCondition(Condition condition);

}
