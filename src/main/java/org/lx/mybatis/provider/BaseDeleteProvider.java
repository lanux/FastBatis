package org.lx.mybatis.provider;

import lx.mybatis.mapper.mapperhelper.EntityHelper;
import org.apache.ibatis.jdbc.SQL;
import org.lx.mybatis.entity.EntityColumn;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.entity.Selectable;
import org.lx.mybatis.helper.ProviderSqlHelper;

import java.util.List;

public class BaseDeleteProvider {

    /**
     * 通过条件删除
     *
     * @param object
     * @return
     */
    public String delete(Selectable object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            DELETE_FROM(entityTable.getName());
            List<EntityColumn> select = object.select(entityTable.getPropertyMap());
            WHERE(ProviderSqlHelper.getEqualsHolder(select));
        }}.toString();
    }

    /**
     * 通过主键删除
     *
     * @param object
     */
    public String deleteByPrimaryKey(Object object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            DELETE_FROM(entityTable.getName());
            WHERE(ProviderSqlHelper.getEqualsHolder(entityTable.getEntityClassPKColumns()));
        }}.toString();
    }
}
