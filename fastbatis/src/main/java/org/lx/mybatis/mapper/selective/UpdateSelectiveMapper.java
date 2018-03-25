package org.lx.mybatis.mapper.selective;

import org.apache.ibatis.annotations.UpdateProvider;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.provider.StaticSqlProvider;

@FastMapper
public interface UpdateSelectiveMapper<T> {

    @UpdateProvider(type = StaticSqlProvider.class, method = "updateSelectiveByPrimaryKey")
    int updateSelectiveByPrimaryKey(T t);
}
