package org.lx.mybatis.helper;

import lx.mybatis.mapper.entity.EntityColumn;
import lx.mybatis.mapper.entity.EntityTable;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ProviderSqlHelper {

    private static final CharSequence COLUMN_JOIN_DELIMITER = ",";

    /**
     * 返回：#{id,jdbcType=NUMERIC},#{name,jdbcType=VARCHAR,typeHandler=MyTypeHandler}
     * @param columnList
     * @return
     */
    public static String getValuesHolder(Collection<EntityColumn> columnList) {
        return columnList.stream().map(EntityColumn::getColumnHolder).collect(Collectors.joining(COLUMN_JOIN_DELIMITER));
    }

    public static String[] getEqualsHolder(Collection<EntityColumn> columnList) {
        return columnList.stream().map(EntityColumn::getColumnEqualsHolder).toArray(String[]::new);
    }


    /**
     * 获取所有查询列，如id,name,code...
     *
     * @param entityClass
     * @return
     */
    public static String getAllColumns(Class<?> entityClass) {
        return getAllColumns(EntityHelper.getEntityTable(entityClass));
    }

    /**
     * 获取所有查询列，如id,name,code...
     *
     * @param table
     * @return
     */
    public static String getAllColumns(EntityTable table) {
        Set<EntityColumn> columnList = table.getEntityClassColumns();
        return getColumns(columnList);
    }

    /**
     * 获取所有查询列，如id,name,code...
     *
     * @param columnList
     * @return
     */
    public static String getColumns(Collection<EntityColumn> columnList) {
        return columnList.stream().map(EntityColumn::getColumn).collect(Collectors.joining(COLUMN_JOIN_DELIMITER));
    }
}
