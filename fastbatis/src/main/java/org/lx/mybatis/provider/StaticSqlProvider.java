package org.lx.mybatis.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.lx.mybatis.entity.EntityColumn;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.helper.EntityHelper;
import org.lx.mybatis.helper.SqlUtil;

import java.util.List;

public class StaticSqlProvider {
    public String insert(Object object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        List<EntityColumn> columns = EntityHelper.getColumns(entityTable.getEntityClassColumns(), false, true, false);
        return new SQL() {{
            INSERT_INTO(entityTable.getName());
            VALUES(SqlUtil.getColumnNames(columns), SqlUtil.getValuesHolder(columns));
        }}.toString();
    }


    public String insertSelective(Object object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        List<EntityColumn> columns = EntityHelper.getColumns(entityTable.getEntityClassColumns(), false, true, false);
        List<EntityColumn> select = EntityHelper.filterNotNull(columns, object);
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
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            DELETE_FROM(entityTable.getName());
            List<EntityColumn> select = EntityHelper.filterNotNull(entityTable.getEntityClassColumns(), object);
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
        EntityTable entityTable = EntityHelper.getEntityTable(providerContext.getMapperType());
        return new SQL() {{
            DELETE_FROM(entityTable.getName());
            WHERE(SqlUtil.getEqualsHolder(entityTable.getEntityClassPKColumns()));
        }}.toString();
    }

    /**
     * 根据主键进行查询
     *
     * @param object
     */
    public String selectByPrimaryKey(Object object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            SELECT(SqlUtil.getAllColumns(entityTable));
            FROM(entityTable.getName());
            WHERE(SqlUtil.getEqualsHolder(entityTable.getEntityClassPKColumns()));
        }}.toString();
    }

    /**
     * 查询
     *
     * @param object
     * @return
     */
    public String selectBySelective(Object object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            SELECT(SqlUtil.getAllColumns(entityTable));
            FROM(entityTable.getName());
            List<EntityColumn> select = EntityHelper.filterNotNull(entityTable.getEntityClassColumns(), object);
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
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        List<EntityColumn> select = EntityHelper.filterNotNull(entityTable.getEntityClassColumns(), object);
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
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            UPDATE(entityTable.getName());
            for (EntityColumn column : entityTable.getEntityClassColumns()) {
                SET(column.getColumnEqualsHolder());
            }
            for (EntityColumn column : entityTable.getEntityClassPKColumns()) {
                WHERE(column.getColumnEqualsHolder());
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
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            UPDATE(entityTable.getName());
            List<EntityColumn> select = EntityHelper.filterNotNull(entityTable.getEntityClassColumns(), object);
            for (EntityColumn column : select) {
                SET(column.getColumnEqualsHolder());
            }
            for (EntityColumn column : entityTable.getEntityClassPKColumns()) {
                WHERE(column.getColumnEqualsHolder());
            }
        }}.toString();
    }


}
