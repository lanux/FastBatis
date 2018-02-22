package org.lx.mybatis.provider;

import org.apache.ibatis.jdbc.SQL;
import org.lx.mybatis.entity.EntityColumn;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.helper.EntityHelper;
import org.lx.mybatis.helper.ProviderSqlHelper;

import java.util.List;

public class BaseDeleteProvider {

    /**
     * 通过条件删除
     *
     * @param object
     * @return
     */
    public String delete(Object object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            DELETE_FROM(entityTable.getName());
            List<EntityColumn> select = EntityHelper.filterNotNull(entityTable.getEntityClassColumns(),object);
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
