package org.lx.mybatis.mapper.base;

public interface SelectMapper<T, K> {

    T selectByPrimaryKey(K k);

}
