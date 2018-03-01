package org.lx.mybatis.mapper.base;

import org.lx.mybatis.annotation.FastMapper;

@FastMapper
public interface DeleteMapper<T> {

    int deleteByPrimaryKey(Object key);
}
