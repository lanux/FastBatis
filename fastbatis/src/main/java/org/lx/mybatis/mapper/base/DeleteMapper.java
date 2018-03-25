package org.lx.mybatis.mapper.base;

import org.apache.ibatis.annotations.DeleteProvider;
import org.lx.mybatis.annotation.FastMapper;

@FastMapper
public interface DeleteMapper<T> {

    @DeleteProvider(type = BaseDeleteProvider.class,method = "deleteByPrimaryKey")
    int deleteByPrimaryKey(Object key);
}
