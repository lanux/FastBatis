package org.lx.mybatis.mapper.base;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.ResultType;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.provider.BaseDeleteProvider;

@FastMapper
public interface DeleteMapper<T> {

    @DeleteProvider(type = BaseDeleteProvider.class, method = "deleteByPrimaryKey")
    @ResultType(Integer.class)
    int deleteByPrimaryKey(T t);

}
