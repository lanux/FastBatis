package org.lx.mybatis.mapper.selective;

import org.apache.ibatis.annotations.SelectProvider;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.provider.BaseSelectProvider;

import java.util.List;

@FastMapper
public interface SelectSelectiveMapper<T> {

    @SelectProvider(type = BaseSelectProvider.class,method = "select")
    List<T> selectSelective(T t);

    int countBySelective(T t);
}
