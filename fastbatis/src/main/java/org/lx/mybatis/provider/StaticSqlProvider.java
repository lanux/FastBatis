package org.lx.mybatis.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.entity.TableColumn;
import org.lx.mybatis.helper.EntityTables;
import org.lx.mybatis.helper.SqlUtil;
import org.lx.mybatis.helper.XmlSqlUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class StaticSqlProvider {
    public String insert(Object object) {
        EntityTable entityTable = EntityTables.getEntityTable(object.getClass());
        List<TableColumn> columns = EntityTables.getColumns(entityTable.getColumns(), false, true, false);
        return new SQL() {{
            INSERT_INTO(entityTable.getName());
            VALUES(SqlUtil.getColumnNames(columns), SqlUtil.getValuesHolder(columns));
        }}.toString();
    }


    public String insertSelective(Object object) {
        EntityTable entityTable = EntityTables.getEntityTable(object.getClass());
        List<TableColumn> columns = EntityTables.getColumns(entityTable.getColumns(), false, true, false);
        List<TableColumn> select = EntityTables.filterNotNull(columns, object);
        return new SQL() {{
            INSERT_INTO(entityTable.getName());
            VALUES(SqlUtil.getColumnNames(select), SqlUtil.getValuesHolder(select));
        }}.toString();
    }

    /**
     * 通过非空字段匹配删除
     *
     * @param object
     * @return
     */
    public String deleteSelective(Object object) {
        EntityTable entityTable = EntityTables.getEntityTable(object.getClass());
        return new SQL() {{
            DELETE_FROM(entityTable.getName());
            List<TableColumn> select = EntityTables.filterNotNull(entityTable.getColumns(), object);
            WHERE(SqlUtil.getEqualsHolder(select));
        }}.toString();
    }

    /**
     * 通过主键删除
     *
     * @param providerContext
     */
    public String deleteByPrimaryKey(ProviderContext providerContext) {
        // 读取泛型对象
        EntityTable entityTable = EntityTables.getEntityTable(getEntityClassByInterface(providerContext.getMapperType()));
        return new SQL() {{
            DELETE_FROM(entityTable.getName());
            WHERE(SqlUtil.getEqualsHolder(entityTable.getKeyColumns()));
        }}.toString();
    }

    /**
     * 根据主键进行查询
     *
     * @param providerContext
     */
    public String selectByPrimaryKey(ProviderContext providerContext) {
        EntityTable entityTable = EntityTables.getEntityTable(getEntityClassByInterface(providerContext.getMapperType()));
        return new SQL() {{
            SELECT(SqlUtil.getAllColumns(entityTable));
            FROM(entityTable.getName());
            WHERE(SqlUtil.getEqualsHolder(entityTable.getKeyColumns()));
        }}.toString();
    }

    /**
     * 根据主键进行查询
     *
     * @param providerContext
     */
    public String selectExcludeBlobByPrimaryKey(ProviderContext providerContext) {
        EntityTable entityTable = EntityTables.getEntityTable(getEntityClassByInterface(providerContext.getMapperType()));
        return new SQL() {{
            SELECT(SqlUtil.getColumnNames(entityTable.getColumns(), true, false, false));
            FROM(entityTable.getName());
            WHERE(SqlUtil.getEqualsHolder(entityTable.getKeyColumns()));
        }}.toString();
    }

    /**
     * 查询
     *
     * @param object
     * @return
     */
    public String selectBySelective(Object object) {
        EntityTable entityTable = EntityTables.getEntityTable(object.getClass());
        return new SQL() {{
            SELECT(SqlUtil.getAllColumns(entityTable));
            FROM(entityTable.getName());
            List<TableColumn> select = EntityTables.filterNotNull(entityTable.getColumns(), object);
            WHERE(SqlUtil.getEqualsHolder(select));
        }}.toString();
    }

    /**
     * 查询
     *
     * @param object
     * @return
     */
    public String selectExcludeBlobBySelective(Object object) {
        EntityTable entityTable = EntityTables.getEntityTable(object.getClass());
        return new SQL() {{
            SELECT(SqlUtil.getColumnNames(entityTable.getColumns(), true, false, false));
            FROM(entityTable.getName());
            List<TableColumn> select = EntityTables.filterNotNull(entityTable.getColumns(), object);
            WHERE(SqlUtil.getEqualsHolder(select));
        }}.toString();
    }


    /**
     * 查询总数
     *
     * @param object
     * @return
     */
    public String countBySelective(Object object) {
        EntityTable entityTable = EntityTables.getEntityTable(object.getClass());
        List<TableColumn> select = EntityTables.filterNotNull(entityTable.getColumns(), object);
        return new SQL() {{
            SELECT("COUNT(1)");
            FROM(entityTable.getName());
            WHERE(SqlUtil.getEqualsHolder(select));
        }}.toString();
    }


    /**
     * 通过主键更新全部字段
     *
     * @param object
     */
    public String updateByPrimaryKey(Object object) {
        EntityTable entityTable = EntityTables.getEntityTable(object.getClass());
        return new SQL() {{
            UPDATE(entityTable.getName());
            for (TableColumn column : entityTable.getColumns()) {
                if (!column.isId()) {
                    SET(XmlSqlUtil.getColumnEqualsValueHolder(column, null, null));
                }
            }
            for (TableColumn column : entityTable.getKeyColumns()) {
                WHERE(XmlSqlUtil.getColumnEqualsValueHolder(column, null, null));
            }
        }}.toString();
    }

    /**
     * 通过主键更新不为null的字段
     *
     * @param object
     * @return
     */
    public String updateSelectiveByPrimaryKey(Object object) {
        EntityTable entityTable = EntityTables.getEntityTable(object.getClass());
        return new SQL() {{
            UPDATE(entityTable.getName());
            List<TableColumn> select = EntityTables.filterNotNull(entityTable.getColumns(), object);
            for (TableColumn column : select) {
                if (!column.isId()) {
                    SET(XmlSqlUtil.getColumnEqualsValueHolder(column, null, null));
                }
            }
            for (TableColumn column : entityTable.getKeyColumns()) {
                WHERE(XmlSqlUtil.getColumnEqualsValueHolder(column, null, null));
            }
        }}.toString();
    }

    private Class<?> getEntityClassByInterface(Class mapperInterface) {
        Type[] types = mapperInterface.getGenericInterfaces();
        if (types != null) {
            for (Type genericSuperclass : types) {
                if (genericSuperclass instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    return (Class) actualTypeArguments[0];
                }
            }
        }
        return null;
    }


}
