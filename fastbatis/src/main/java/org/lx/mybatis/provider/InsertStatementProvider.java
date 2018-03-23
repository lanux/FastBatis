package org.lx.mybatis.provider;

import org.lx.mybatis.entity.EntityColumn;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.helper.EntityHelper;
import org.lx.mybatis.helper.SqlUtil;
import org.lx.mybatis.helper.XmlSqlUtil;

import java.util.List;

/**
 * Created by lanux on 2018/2/23.
 */
public class InsertStatementProvider {

    public String insert(Class object) {
        StringBuilder sb = new StringBuilder();
        EntityTable entityTable = EntityHelper.getEntityTable(object);
        List<EntityColumn> columns = EntityHelper.getColumns(entityTable.getEntityClassColumns(), false, true, false);
        sb.append(SqlUtil.insertIntoTable(entityTable.getName()));
        sb.append("(");
        sb.append(SqlUtil.getColumnNames(columns));
        sb.append(") VALUES (");
        sb.append(SqlUtil.getValuesHolder(columns));
        sb.append(")");
        return sb.toString();
    }


    public String batchInsert(Class object) {
        StringBuilder sb = new StringBuilder();
        EntityTable entityTable = EntityHelper.getEntityTable(object);
        List<EntityColumn> columns = EntityHelper.getColumns(entityTable.getEntityClassColumns(), false, true, false);
        sb.append(SqlUtil.insertIntoTable(entityTable.getName()));
        sb.append("(");
        sb.append(SqlUtil.getColumnNames(columns));
        sb.append(")");
        sb.append(XmlSqlUtil.batchInsertValues(columns));
        return sb.toString();
    }

    public String xmlInsertSelective(Class object) {
        StringBuilder sb = new StringBuilder();
        EntityTable entityTable = EntityHelper.getEntityTable(object);
        List<EntityColumn> columns = EntityHelper.getColumns(entityTable.getEntityClassColumns(), false, true, false);
        sb.append(SqlUtil.insertIntoTable(entityTable.getName()));
        sb.append("(");
        sb.append(SqlUtil.getColumnNames(columns));
        sb.append(") VALUES (");
        sb.append(XmlSqlUtil.getAllIfColumns(null, columns));
        sb.append(")");
        return sb.toString();
    }


    public String insertSelective(Class object) {
        StringBuilder sb = new StringBuilder();
        EntityTable entityTable = EntityHelper.getEntityTable(object);
        List<EntityColumn> columns = EntityHelper.getColumns(entityTable.getEntityClassColumns(), false, true, false);
        sb.append(SqlUtil.insertIntoTable(entityTable.getName()));
        sb.append("(");
        sb.append(XmlSqlUtil.getAllIfColumns(null, columns));
        sb.append(") VALUES (");
        sb.append(XmlSqlUtil.getAllIfColumnValueHolder(null, columns));
        sb.append(")");
        return sb.toString();
    }
}
