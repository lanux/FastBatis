package org.lx.mybatis.mapper.base;

import org.lx.mybatis.annotation.FastMapper;

@FastMapper
public interface SelectMapper<T> {

    T selectByPrimaryKey(Object key);

}
