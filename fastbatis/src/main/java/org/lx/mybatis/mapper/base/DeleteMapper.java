package org.lx.mybatis.mapper.base;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.mapping.SqlCommandType;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.annotation.StatementProvider;
import org.lx.mybatis.provider.DynamicSqlProvider;

@FastMapper
public interface DeleteMapper<T> {

    /**
     * @param key
     * @return
     */
    @StatementProvider(type = DynamicSqlProvider.class, method = "deleteByPrimaryKey",commandType = SqlCommandType.DELETE)
    int deleteByPrimaryKey(Object key);
}
