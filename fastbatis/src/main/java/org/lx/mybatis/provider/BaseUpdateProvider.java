package org.lx.mybatis.provider;

import org.apache.ibatis.jdbc.SQL;
import org.lx.mybatis.entity.Condition;
import org.lx.mybatis.entity.EntityColumn;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.helper.EntityHelper;
import org.lx.mybatis.helper.SqlUtil;
import org.lx.mybatis.helper.XmlSqlUtil;

import java.util.List;


public class BaseUpdateProvider {


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
    public String updateByPrimaryKeySelective(Object object) {
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

    /**
     * 通过主键更新不为null的字段
     *
     * @param condition
     * @return
     */
    public String updateByCondition(Condition condition, boolean notNull, boolean notEmpty) {
        EntityTable entityTable = EntityHelper.getEntityTable(condition.getEntityClass());
        List<EntityColumn> columns = EntityHelper.getColumns(entityTable.getEntityClassColumns(), false, false, true);
        String s = XmlSqlUtil.updateSetColumns(columns, condition.getAlias(), notNull, notEmpty);
        StringBuilder sb = new StringBuilder();
        sb.append(SqlUtil.updateTable(entityTable.getName(),condition.getAlias()));
        sb.append(s);
        sb.append(XmlSqlUtil.whereClause(condition));
        return sb.toString();
    }
}
