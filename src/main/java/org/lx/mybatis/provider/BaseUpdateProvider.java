package org.lx.mybatis.provider;

import org.apache.ibatis.jdbc.SQL;
import org.lx.mybatis.entity.EntityColumn;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.entity.Selectable;
import org.lx.mybatis.helper.EntityHelper;

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
    public String updateByPrimaryKeySelective(Selectable object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            UPDATE(entityTable.getName());
            List<EntityColumn> select = object.select(entityTable.getPropertyMap());
            for (EntityColumn column : select) {
                SET(column.getColumnEqualsHolder());
            }
            for (EntityColumn column : entityTable.getEntityClassPKColumns()) {
                WHERE(column.getColumnEqualsHolder());
            }
        }}.toString();
    }
}
