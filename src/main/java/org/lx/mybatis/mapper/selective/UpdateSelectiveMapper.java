package org.lx.mybatis.mapper.selective;

public interface UpdateSelectiveMapper<T> {
    int updateSelective(T t);
}
