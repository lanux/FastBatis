package org.lx.mybatis.mapper.selective;

import org.lx.mybatis.annotation.FastMapper;

@FastMapper
public interface CrudSelectiveMapper<T> extends DeleteSelectiveMapper<T>, UpdateSelectiveMapper<T>, InsertSelectiveMapper<T>, SelectSelectiveMapper<T> {
}
