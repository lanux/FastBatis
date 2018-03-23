package org.lx.mybatis.mapper.selective;

import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.annotation.StatementProvider;
import org.lx.mybatis.provider.BaseDeleteProvider;

@FastMapper
public interface DeleteSelectiveMapper<T> {

    @StatementProvider(BaseDeleteProvider.class)
    int deleteSelective(T t);
}
