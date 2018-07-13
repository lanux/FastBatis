package org.lx.mybatis.mapper.base;

import org.apache.ibatis.mapping.SqlCommandType;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.annotation.StatementProvider;
import org.lx.mybatis.entity.Options;
import org.lx.mybatis.provider.DynamicSqlProvider;

@FastMapper
public interface InsertMapper<T> {

    @StatementProvider(type = DynamicSqlProvider.class, method = "insert", commandType = SqlCommandType.INSERT)
    int insert(T t);

}
