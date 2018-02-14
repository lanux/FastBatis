package org.lx.mybatis.provider;

import lx.mybatis.mapper.mapperhelper.EntityHelper;
import org.apache.ibatis.jdbc.SQL;
import org.lx.mybatis.entity.Condition;
import org.lx.mybatis.entity.EntityColumn;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.entity.Selectable;
import org.lx.mybatis.helper.ProviderSqlHelper;

import java.util.List;

public class BaseSelectProvider {

    /**
     * 查询
     *
     * @param object
     * @return
     */
    public String selectOne(Selectable object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            SELECT(ProviderSqlHelper.getAllColumns(entityTable));
            FROM(entityTable.getName());
            List<EntityColumn> select = object.select(entityTable.getPropertyMap());
            WHERE(ProviderSqlHelper.getEqualsHolder(select));
        }}.toString();
    }

    /**
     * 查询
     *
     * @param object
     * @return
     */
    public String select(Selectable object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            SELECT(ProviderSqlHelper.getAllColumns(entityTable));
            FROM(entityTable.getName());
            List<EntityColumn> select = object.select(entityTable.getPropertyMap());
            WHERE(ProviderSqlHelper.getEqualsHolder(select));
        }}.toString();
    }

    /**
     * 根据主键进行查询
     *
     * @param object
     */
    public String selectByPrimaryKey(Selectable object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            SELECT(ProviderSqlHelper.getAllColumns(entityTable));
            FROM(entityTable.getName());
            WHERE(ProviderSqlHelper.getEqualsHolder(entityTable.getEntityClassPKColumns()));
        }}.toString();
    }

    /**
     * 查询总数
     *
     * @param object
     * @return
     */
    public String selectCount(Selectable object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            SELECT("COUNT(1)");
            FROM(entityTable.getName());
            WHERE(ProviderSqlHelper.getEqualsHolder(entityTable.getEntityClassPKColumns()));
        }}.toString();
    }


    /**
     * 查询全部结果
     *
     * @param condition
     * @return
     */
    public String selectAll(Condition condition) {
        EntityTable entityTable = EntityHelper.getEntityTable(condition.getEntityClass());
        return new SQL() {{
            SELECT(ProviderSqlHelper.getAllColumns(entityTable));
            FROM(entityTable.getName());
            WHERE(ProviderSqlHelper.whereClause(condition));
        }}.toString();
    }
}
