package org.lx.mybatis.mapper.base;

import org.apache.ibatis.annotations.UpdateProvider;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.provider.StaticSqlProvider;

@FastMapper
public interface UpdateMapper<T> {

    @UpdateProvider(type = StaticSqlProvider.class, method = "updateByPrimaryKey")
    int update(T t);

}
