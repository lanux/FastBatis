package org.lx.mybatis.provider;

import org.apache.ibatis.jdbc.SQL;
import org.lx.mybatis.entity.EntityColumn;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.helper.EntityHelper;
import org.lx.mybatis.helper.SqlUtil;

import java.util.List;
import java.util.stream.Collectors;

public class BaseInsertProvider {

    public String insert(Object object) {
        EntityTable entityTable = EntityHelper.getEntityTable(object.getClass());
        List<EntityColumn> columns = EntityHelper.getColumns(entityTable.getEntityClassColumns(), false, true, false);
        return new SQL() {{
            INSERT_INTO(entityTable.getName());
            String columnNames = entityTable.getEntityClassColumns().stream().map(EntityColumn::getColumn).collect(Collectors.joining(","));
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
}
