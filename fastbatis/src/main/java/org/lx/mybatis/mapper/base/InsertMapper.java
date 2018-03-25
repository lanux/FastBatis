package org.lx.mybatis.mapper.base;

import org.apache.ibatis.annotations.InsertProvider;
import org.lx.mybatis.annotation.FastMapper;

@FastMapper
public interface InsertMapper<T> {

    @InsertProvider(type = BaseInsertProvider.class, method = "insert")
    int insert(T t);

}
