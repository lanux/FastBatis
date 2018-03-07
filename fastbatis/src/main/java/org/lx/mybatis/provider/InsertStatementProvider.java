package org.lx.mybatis.provider;

import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.helper.EntityHelper;
import org.lx.mybatis.helper.ProviderSqlHelper;

/**
 * Created by lanux on 2018/2/23.
 */
public class InsertStatementProvider {

    public String insert(Class object) {
        StringBuilder sb = new StringBuilder();
        EntityTable entityTable = EntityHelper.getEntityTable(object);
        sb.append(ProviderSqlHelper.insertIntoTable(entityTable.getName()));
        sb.append("(");
        sb.append(ProviderSqlHelper.getAllColumns(entityTable));
        sb.append(") VALUES (");
        sb.append(ProviderSqlHelper.getValuesHolder(entityTable.getEntityClassColumns()));
        sb.append(")");
        return sb.toString();
    }

    public String insertSelective(Class object) {
        StringBuilder sb = new StringBuilder();
        EntityTable entityTable = EntityHelper.getEntityTable(object);
        sb.append(ProviderSqlHelper.insertIntoTable(entityTable.getName()));
        sb.append("(");
        sb.append(ProviderSqlHelper.getAllIfColumns(null,entityTable.getEntityClassColumns()));
        sb.append(") VALUES (");
        sb.append(ProviderSqlHelper.getAllIfColumnValueHolder(null,entityTable.getEntityClassColumns()));
        sb.append(")");
        return sb.toString();
    }
}
