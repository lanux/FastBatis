package org.lx.mybatis.mapper.base;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultType;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.entity.Selectable;
import org.lx.mybatis.provider.BaseInsertProvider;

@FastMapper
public interface InsertMapper<T extends Selectable> {

    @InsertProvider(type = BaseInsertProvider.class, method = "insert")
    @ResultType(Integer.class)
    @Options(useGeneratedKeys = true)
    int insert(T t);

}
