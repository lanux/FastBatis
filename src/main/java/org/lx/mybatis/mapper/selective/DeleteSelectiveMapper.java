package org.lx.mybatis.mapper.selective;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.ResultType;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.entity.Selectable;
import org.lx.mybatis.provider.BaseDeleteProvider;

@FastMapper
public interface DeleteSelectiveMapper<T extends Selectable> {

    @DeleteProvider(type = BaseDeleteProvider.class, method = "delete")
    @ResultType(Integer.class)
    int deleteSelective(T t);
}
