package org.lx.mybatis.mapper.base;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.provider.StaticSqlProvider;

@FastMapper
public interface SelectMapper<T> {

    @SelectProvider(type = StaticSqlProvider.class, method = "selectByPrimaryKey")
    @Options(fetchSize = 1)
    @ResultMap("BaseResultMap")
    T selectByPrimaryKey(Object key);

}
