package org.lx.mybatis.mapper.selective;

import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.entity.Selectable;

@FastMapper
public interface InsertSelectiveMapper<T> {
    int insertSelective(T t);
}
