package org.lx.mybatis.mapper.selective;

import org.lx.mybatis.annotation.FastMapper;

@FastMapper
public interface DeleteSelectiveMapper<T> {

    int deleteSelective(T t);
}
