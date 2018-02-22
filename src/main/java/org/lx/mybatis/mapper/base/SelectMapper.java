package org.lx.mybatis.mapper.base;

import org.lx.mybatis.annotation.FastMapper;

@FastMapper
public interface SelectMapper<T, K> {

    T selectByPrimaryKey(K k);

}
