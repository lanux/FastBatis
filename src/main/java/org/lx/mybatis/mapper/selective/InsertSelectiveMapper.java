package org.lx.mybatis.mapper.selective;

import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.entity.Selectable;

@FastMapper
public interface InsertSelectiveMapper<T extends Selectable> {
    int insertSelective(T t);
}
