package org.lx.mybatis.mapper.selective;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.provider.BaseInsertProvider;

@FastMapper
public interface InsertSelectiveMapper<T> {
    @InsertProvider(type = BaseInsertProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    int insertSelective(T t);
}
