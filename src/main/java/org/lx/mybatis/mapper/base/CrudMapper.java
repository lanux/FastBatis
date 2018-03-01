package org.lx.mybatis.mapper.base;

import org.lx.mybatis.annotation.FastMapper;

@FastMapper
public interface CrudMapper<T> extends DeleteMapper<T>, UpdateMapper<T>, InsertMapper<T>, SelectMapper<T> {
}
