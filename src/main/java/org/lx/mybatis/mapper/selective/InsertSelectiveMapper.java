package org.lx.mybatis.mapper.selective;

import org.lx.mybatis.annotation.FastMapper;

@FastMapper
public interface InsertSelectiveMapper<T> {
    int insertSelective(T t);
}
