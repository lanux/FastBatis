package org.lx.mybatis.mapper.base;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.mapping.SqlCommandType;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.annotation.StatementProvider;
import org.lx.mybatis.provider.DynamicSqlProvider;
import org.lx.mybatis.provider.StaticSqlProvider;

@FastMapper
public interface InsertMapper<T> {

    @StatementProvider(type = DynamicSqlProvider.class, method = "insert",commandType = SqlCommandType.INSERT)
    int insert(T t);

}
