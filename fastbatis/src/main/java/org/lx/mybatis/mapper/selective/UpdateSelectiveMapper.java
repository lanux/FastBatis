package org.lx.mybatis.mapper.selective;

import org.lx.mybatis.annotation.FastMapper;

@FastMapper
public interface UpdateSelectiveMapper<T> {
    int updateSelective(T t);
}
