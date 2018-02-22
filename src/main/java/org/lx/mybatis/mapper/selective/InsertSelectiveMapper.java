package org.lx.mybatis.mapper.selective;

public interface InsertSelectiveMapper<T> {
    int insertSelective(T t);
}
