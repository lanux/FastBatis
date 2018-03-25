package org.lx.mybatis.mapper.base;

import org.apache.ibatis.annotations.InsertProvider;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.provider.StaticSqlProvider;

@FastMapper
public interface InsertMapper<T> {

    @InsertProvider(type = StaticSqlProvider.class, method = "insert")
    int insert(T t);

}
