package org.lx.mybatis.mapper.base;

import org.lx.mybatis.annotation.FastMapper;

@FastMapper
public interface InsertMapper<T> {

    int insert(T t);

}
