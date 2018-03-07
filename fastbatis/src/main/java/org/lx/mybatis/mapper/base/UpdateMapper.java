package org.lx.mybatis.mapper.base;

import org.lx.mybatis.annotation.FastMapper;

@FastMapper
public interface UpdateMapper<T> {

    int update(T t);

}
