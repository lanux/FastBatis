package org.lx.mybatis.mapper.selective;

import org.apache.ibatis.annotations.InsertProvider;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.provider.StaticSqlProvider;

@FastMapper
public interface InsertSelectiveMapper<T> {
    @InsertProvider(type = StaticSqlProvider.class, method = "insertSelective")
    int insertSelective(T t);
}
