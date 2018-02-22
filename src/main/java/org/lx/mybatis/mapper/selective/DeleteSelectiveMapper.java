package org.lx.mybatis.mapper.selective;

public interface DeleteSelectiveMapper<T> {

    int deleteSelective(T t);
}
