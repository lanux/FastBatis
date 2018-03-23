package org.lx.mybatis.provider;

import org.apache.ibatis.jdbc.SQL;
import org.lx.mybatis.entity.EntityColumn;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.entity.SelectCondition;
import org.lx.mybatis.helper.EntityHelper;
import org.lx.mybatis.helper.SqlUtil;
import org.lx.mybatis.helper.XmlSqlUtil;

import java.util.List;

public class BaseSelectProvider {


    /**
     * 查询
     *
     * @param object
     * @return
     */
    public String select(Object object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            SELECT(SqlUtil.getAllColumns(entityTable));
            FROM(entityTable.getName());
            List<EntityColumn> select = EntityHelper.filterNotNull(entityTable.getEntityClassColumns(), object);
            WHERE(SqlUtil.getEqualsHolder(select));
        }}.toString();
    }

    public String select(Class clazz) {
        EntityTable entityTable = EntityHelper.getEntityTable(clazz);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " + SqlUtil.getAllColumns(entityTable) + SqlUtil.fromTable(entityTable.getName()));
        sb.append(XmlSqlUtil.whereAllIfColumns(null, entityTable.getEntityClassColumns()));
        return sb.toString();
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
     * 查询总数
     *
     * @param object
     * @return
     */
    public String selectCount(Object object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            SELECT("COUNT(1)");
            FROM(entityTable.getName());
            WHERE(SqlUtil.getEqualsHolder(entityTable.getEntityClassPKColumns()));
        }}.toString();
    }


    /**
     * 查询全部结果
     *
     * @param condition
     * @return
     */
    public String xmlSelectByCondition(SelectCondition condition) {
        EntityTable entityTable = EntityHelper.getEntityTable(condition.getEntityClass());
        return new SQL() {{
            SELECT(SqlUtil.getAllColumns(entityTable));
            FROM(entityTable.getName());
            WHERE(XmlSqlUtil.xmlWhereClause(condition));
        }}.toString();
    }
}
