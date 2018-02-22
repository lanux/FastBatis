package org.lx.mybatis.mapper.base;

public interface DeleteMapper<T> {

    int deleteByPrimaryKey(T t);

}
