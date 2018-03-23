package org.lx.mybatis.provider;

import org.apache.ibatis.jdbc.SQL;
import org.lx.mybatis.entity.Condition;
import org.lx.mybatis.entity.EntityColumn;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.helper.EntityHelper;
import org.lx.mybatis.helper.SqlUtil;
import org.lx.mybatis.helper.XmlSqlUtil;

import java.util.List;

public class BaseDeleteProvider {

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
     * @param clazz
     */
    public String deleteByPrimaryKey(Class clazz) {
        EntityTable entityTable = EntityHelper.getEntityTable(clazz);
        return new SQL() {{
            DELETE_FROM(entityTable.getName());
            WHERE(SqlUtil.getEqualsHolder(entityTable.getEntityClassPKColumns()));
        }}.toString();
    }

    /**
     * 通过非空字段匹配删除
     *
     * @param clazz
     * @return
     */
    public String xmlDelete(Class clazz) {
        EntityTable entityTable = EntityHelper.getEntityTable(clazz);
        return new SQL() {{
            DELETE_FROM(entityTable.getName());
            WHERE(XmlSqlUtil.getAllIfColumnValueHolder(null, entityTable.getEntityClassColumns()));
        }}.toString();
    }

    /**
     * 通过主键删除
     *
     * @param object
     */
    public String xmlDeleteByPrimaryKey(Object object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            DELETE_FROM(entityTable.getName());
            WHERE(SqlUtil.getEqualsHolder(entityTable.getEntityClassPKColumns()));
        }}.toString();
    }

    /**
     * 通过条件删除
     *
     * @param condition
     */
    public String xmlDeleteByCondition(Condition condition) {
        EntityTable entityTable = EntityHelper.getEntityTable(condition.getEntityClass());
        return new SQL() {{
            DELETE_FROM(entityTable.getName());
            WHERE(XmlSqlUtil.whereClause(condition));
        }}.toString();
    }
}
