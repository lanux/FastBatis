package org.lx.mybatis.mapper.selective;

import org.apache.ibatis.annotations.DeleteProvider;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.provider.StaticSqlProvider;

@FastMapper
public interface DeleteSelectiveMapper<T> {

    @DeleteProvider(type = StaticSqlProvider.class, method = "deleteSelective")
    int deleteSelective(T t);
}
