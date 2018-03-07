package org.lx.mybatis.provider;

import org.apache.ibatis.jdbc.SQL;
import org.lx.mybatis.entity.EntityColumn;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.helper.EntityHelper;
import org.lx.mybatis.helper.ProviderSqlHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BaseInsertProvider {

    public String insert(Object object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            INSERT_INTO(entityTable.getName());
            String columns = entityTable.getEntityClassColumns().stream().map(EntityColumn::getColumn).collect(Collectors.joining(","));
            VALUES(columns, ProviderSqlHelper.getValuesHolder(entityTable.getEntityClassColumns()));
        }}.toString();
    }

    public String insertSelective(Object object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        return new SQL() {{
            INSERT_INTO(entityTable.getName());
            List<EntityColumn> select = EntityHelper.filterNotNull(entityTable.getEntityClassColumns(),object);
            Collection<EntityColumn> list = new ArrayList<>(select);
            String columns = select.stream().map(EntityColumn::getColumn).collect(Collectors.joining(","));
            VALUES(columns, ProviderSqlHelper.getValuesHolder(select));
        }}.toString();
    }
}
