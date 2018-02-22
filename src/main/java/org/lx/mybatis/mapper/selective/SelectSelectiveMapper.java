package org.lx.mybatis.mapper.selective;

import java.util.List;

public interface SelectSelectiveMapper<T> {
    List<T> selectSelective(T t);
}
