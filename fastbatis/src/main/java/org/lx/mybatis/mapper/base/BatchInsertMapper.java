package org.lx.mybatis.mapper.base;

import org.apache.ibatis.mapping.SqlCommandType;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.annotation.StatementProvider;
import org.lx.mybatis.provider.DynamicSqlProvider;

import java.util.Collection;

@FastMapper
public interface BatchInsertMapper<T> {

    @StatementProvider(type = DynamicSqlProvider.class, method = "batchInsert",commandType = SqlCommandType.INSERT)
    int insert(Collection<T> collection);
}
