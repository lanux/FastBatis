package org.lx.mybatis.mapper.selective;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.provider.StaticSqlProvider;

import java.util.List;

@FastMapper
public interface SelectSelectiveMapper<T> {

    @ResultMap("BaseResultMap")
    @SelectProvider(type = StaticSqlProvider.class, method = "selectBySelective")
    List<T> selectBySelective(T t);

    @SelectProvider(type = StaticSqlProvider.class, method = "countBySelective")
    int countBySelective(T t);
}
