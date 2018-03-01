package org.lx.mybatis.mapper.base;

import org.lx.mybatis.annotation.FastMapper;

import java.util.Collection;

@FastMapper
public interface BatchInsertMapper<T> {
    int insert(Collection<T> collection);
}
