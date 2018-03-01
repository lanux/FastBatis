package org.lx.mybatis.mapper.selective;

import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.entity.Selectable;

import java.util.List;

@FastMapper
public interface SelectSelectiveMapper<T> {

    List<T> selectSelective(T t);

    int countBySelective(T t);
}
