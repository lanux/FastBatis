package org.lx.mybatis.provider;

import org.lx.mybatis.builder.StatementProviderContext;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.entity.TableColumn;
import org.lx.mybatis.helper.EntityTables;
import org.lx.mybatis.helper.SqlUtil;
import org.lx.mybatis.helper.XmlSqlUtil;
import org.lx.mybatis.util.StringUtil;

import java.util.List;

public class DynamicSqlProvider {
    /**
     * 新增
     *
     * @param object
     * @return
     */
    public String insert(StatementProviderContext object) {
        StringBuilder sb = new StringBuilder();
        EntityTable entityTable = EntityTables.getEntityTable(object.getEntityClass());
        List<TableColumn> columns = EntityTables.getColumns(entityTable.getColumns(), false, true, false);
        sb.append(SqlUtil.insertIntoTable(entityTable.getName()));
        sb.append("(");
        sb.append(SqlUtil.getColumnNames(columns));
        sb.append(") VALUES (");
        for (TableColumn column : columns) {
            if (StringUtil.isNotBlank(column.getDefaultValue())) {
                sb.append(SqlUtil.NEW_LINE_DELIMITER);
                sb.append(XmlSqlUtil
                        .getIfDefaultValue(column, column.getDefaultValue(), null, SqlUtil.COLUMN_JOIN_DELIMITER));
                sb.append(SqlUtil.NEW_LINE_DELIMITER);
                sb.append(XmlSqlUtil.getIfNotNull(null, column,
                        XmlSqlUtil.getValueHolder(column, null, SqlUtil.COLUMN_JOIN_DELIMITER)));
            } else {
                sb.append(XmlSqlUtil.getValueHolder(column, null, SqlUtil.COLUMN_JOIN_DELIMITER));
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 批量insert
     *
     * @param context
     * @return
     */
    public String batchInsert(StatementProviderContext context) {
        StringBuilder sb = new StringBuilder();
        EntityTable entityTable = EntityTables.getEntityTable(context.getEntityClass());
        List<TableColumn> columns = EntityTables.getColumns(entityTable.getColumns(), false, true, false);
        sb.append(SqlUtil.insertIntoTable(entityTable.getName()));
        sb.append("(");
        sb.append(SqlUtil.getColumnNames(columns));
        sb.append(")");
        sb.append(SqlUtil.NEW_LINE_DELIMITER);
        sb.append(XmlSqlUtil.batchInsertValues(columns));
        return sb.toString();
    }

    /**
     * @param context
     * @return
     */
    public String insertSelective(StatementProviderContext context) {
        StringBuilder sb = new StringBuilder();
        EntityTable entityTable = EntityTables.getEntityTable(context.getEntityClass());
        List<TableColumn> columns = EntityTables.getColumns(entityTable.getColumns(), false, true, false);
        sb.append(SqlUtil.insertIntoTable(entityTable.getName()));
        sb.append(SqlUtil.NEW_LINE_DELIMITER);
        sb.append("<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >");
        for (TableColumn column : columns) {
            if (StringUtil.isNotBlank(column.getDefaultValue())) {
                sb.append(column.getColumn());
                sb.append(SqlUtil.NEW_LINE_DELIMITER);
            } else {
                sb.append(XmlSqlUtil.getIfNotNull(null, column, column.getColumn() + SqlUtil.COLUMN_JOIN_DELIMITER));
            }
        }
        sb.append(SqlUtil.NEW_LINE_DELIMITER);
        sb.append("</trim>");
        sb.append(SqlUtil.NEW_LINE_DELIMITER);
        sb.append("VALUES");
        sb.append(SqlUtil.NEW_LINE_DELIMITER);
        sb.append("<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >");
        for (TableColumn column : columns) {
            if (StringUtil.isNotBlank(column.getDefaultValue())) {
                sb.append(SqlUtil.NEW_LINE_DELIMITER);
                sb.append(XmlSqlUtil
                        .getIfDefaultValue(column, column.getDefaultValue(), null, SqlUtil.COLUMN_JOIN_DELIMITER));
                sb.append(SqlUtil.NEW_LINE_DELIMITER);
                sb.append(XmlSqlUtil.getIfNotNull(null, column,
                        XmlSqlUtil.getValueHolder(column, null, SqlUtil.COLUMN_JOIN_DELIMITER)));
            } else {
                sb.append(XmlSqlUtil.getIfNotNull(null, column, column.getColumn() + SqlUtil.COLUMN_JOIN_DELIMITER));
            }
        } sb.append("</trim>");
        sb.append(SqlUtil.NEW_LINE_DELIMITER);
        return sb.toString();
    }

    /**
     * 通过非空字段匹配删除
     *
     * @param clazz
     * @return
     */
    public String deleteBySelective(Class clazz) {
        EntityTable entityTable = EntityTables.getEntityTable(clazz);
        StringBuilder sb = new StringBuilder();
        sb.append(SqlUtil.deleteFromTable(entityTable.getName()));
        sb.append(SqlUtil.NEW_LINE_DELIMITER);
        sb.append(XmlSqlUtil.whereAllIfColumns(null, entityTable.getColumns()));
        return sb.toString();
    }

    /**
     * 通过主键删除
     *
     * @param context
     */
    public String deleteByPrimaryKey(StatementProviderContext context) {
        EntityTable entityTable = EntityTables.getEntityTable(context.getEntityClass());
        StringBuilder sb = new StringBuilder();
        sb.append(SqlUtil.deleteFromTable(entityTable.getName()));
        sb.append(SqlUtil.NEW_LINE_DELIMITER);
        sb.append(XmlSqlUtil.whereAllIfColumns(null, entityTable.getKeyColumns()));
        return sb.toString();
    }

    /**
     * 通过条件删除
     *
     * @param context
     */
    public String deleteByCondition(StatementProviderContext context) {
        EntityTable entityTable = EntityTables.getEntityTable(context.getEntityClass());
        StringBuilder sb = new StringBuilder();
        sb.append(SqlUtil.deleteFromTable(entityTable.getName()));
        sb.append(SqlUtil.NEW_LINE_DELIMITER);
        sb.append(XmlSqlUtil.whereClause(null));
        return sb.toString();
    }

    /**
     * 非空字段过滤查询
     *
     * @param context
     * @return
     */
    public String selectBySelective(StatementProviderContext context) {
        EntityTable entityTable = EntityTables.getEntityTable(context.getEntityClass());
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(SqlUtil.getAllColumns(entityTable));
        sb.append(SqlUtil.fromTable(entityTable.getName()));
        sb.append(XmlSqlUtil.whereAllIfColumns(null, entityTable.getColumns()));
        return sb.toString();
    }

    /**
     * 查询全部结果
     *
     * @param context
     * @return
     */
    public String selectByCondition(StatementProviderContext context) {
        EntityTable entityTable = EntityTables.getEntityTable(context.getEntityClass());
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(SqlUtil.getAllColumns(entityTable));
        sb.append(SqlUtil.fromTable(entityTable.getName()));
        sb.append(XmlSqlUtil.whereClause(null));
        return sb.toString();
    }

    /**
     * 查询数量
     *
     * @param context
     * @return
     */
    public String countByCondition(StatementProviderContext context) {
        EntityTable entityTable = EntityTables.getEntityTable(context.getEntityClass());
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("COUNT(1) ");
        sb.append(SqlUtil.fromTable(entityTable.getName()));
        sb.append(XmlSqlUtil.whereClause(null));
        return sb.toString();
    }

    /**
     * 通过主键更新不为null的字段
     *
     * @param context
     * @return
     */
    public String updateSelectiveByCondition(StatementProviderContext context) {
        EntityTable entityTable = EntityTables.getEntityTable(context.getEntityClass());
        List<TableColumn> columns = EntityTables.getColumns(entityTable.getColumns(), false, false, true);
        String s = XmlSqlUtil.updateSetColumns(columns, null, true, false);
        StringBuilder sb = new StringBuilder();
        sb.append(SqlUtil.updateTable(entityTable.getName(), null));
        sb.append(s);
        sb.append(XmlSqlUtil.whereClause(null));
        return sb.toString();
    }
}
