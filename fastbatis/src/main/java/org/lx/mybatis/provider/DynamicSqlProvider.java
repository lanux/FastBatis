package org.lx.mybatis.provider;

import org.lx.mybatis.builder.StatementProviderContext;
import org.lx.mybatis.entity.EntityColumn;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.helper.EntityHolder;
import org.lx.mybatis.helper.SqlUtil;
import org.lx.mybatis.helper.XmlSqlUtil;

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
        EntityTable entityTable = EntityHolder.getEntityTable(object.getEntityClass());
        List<EntityColumn> columns = EntityHolder.getColumns(entityTable.getEntityClassColumns(), false, true, false);
        sb.append(SqlUtil.insertIntoTable(entityTable.getName()));
        sb.append("(");
        sb.append(SqlUtil.getColumnNames(columns));
        sb.append(") VALUES (");
        sb.append(SqlUtil.getValuesHolder(columns));
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
        EntityTable entityTable = EntityHolder.getEntityTable(context.getEntityClass());
        List<EntityColumn> columns = EntityHolder.getColumns(entityTable.getEntityClassColumns(), false, true, false);
        sb.append(SqlUtil.insertIntoTable(entityTable.getName()));
        sb.append("(");
        sb.append(SqlUtil.getColumnNames(columns));
        sb.append(")");
        sb.append(XmlSqlUtil.batchInsertValues(columns));
        return sb.toString();
    }

    /**
     * @param context
     * @return
     */
    public String insertSelective(StatementProviderContext context) {
        StringBuilder sb = new StringBuilder();
        EntityTable entityTable = EntityHolder.getEntityTable(context.getEntityClass());
        List<EntityColumn> columns = EntityHolder.getColumns(entityTable.getEntityClassColumns(), false, true, false);
        sb.append(SqlUtil.insertIntoTable(entityTable.getName()));
        sb.append("(");
        sb.append(SqlUtil.getColumnNames(columns));
        sb.append(") VALUES (");
        sb.append(XmlSqlUtil.getAllIfColumnValueHolder(null, columns));
        sb.append(")");
        return sb.toString();
    }

    /**
     * 通过非空字段匹配删除
     *
     * @param clazz
     * @return
     */
    public String deleteBySelective(Class clazz) {
        EntityTable entityTable = EntityHolder.getEntityTable(clazz);
        StringBuilder sb = new StringBuilder();
        sb.append(SqlUtil.deleteFromTable(entityTable.getName()));
        sb.append(XmlSqlUtil.whereAllIfColumns(null, entityTable.getEntityClassColumns()));
        return sb.toString();
    }

    /**
     * 通过主键删除
     *
     * @param context
     */
    public String deleteByPrimaryKey(StatementProviderContext context) {
        EntityTable entityTable = EntityHolder.getEntityTable(context.getEntityClass());
        StringBuilder sb = new StringBuilder();
        sb.append(SqlUtil.deleteFromTable(entityTable.getName()));
        sb.append(XmlSqlUtil.whereAllIfColumns(null, entityTable.getEntityClassPKColumns()));
        return sb.toString();
    }

    /**
     * 通过条件删除
     *
     * @param context
     */
    public String deleteByCondition(StatementProviderContext context) {
        EntityTable entityTable = EntityHolder.getEntityTable(context.getEntityClass());
        StringBuilder sb = new StringBuilder();
        sb.append(SqlUtil.deleteFromTable(entityTable.getName()));
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
        EntityTable entityTable = EntityHolder.getEntityTable(context.getEntityClass());
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(SqlUtil.getAllColumns(entityTable));
        sb.append(SqlUtil.fromTable(entityTable.getName()));
        sb.append(XmlSqlUtil.whereAllIfColumns(null, entityTable.getEntityClassColumns()));
        return sb.toString();
    }

    /**
     * 查询全部结果
     *
     * @param context
     * @return
     */
    public String selectByCondition(StatementProviderContext context) {
        EntityTable entityTable = EntityHolder.getEntityTable(context.getEntityClass());
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
        EntityTable entityTable = EntityHolder.getEntityTable(context.getEntityClass());
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
        EntityTable entityTable = EntityHolder.getEntityTable(context.getEntityClass());
        List<EntityColumn> columns = EntityHolder.getColumns(entityTable.getEntityClassColumns(), false, false, true);
        String s = XmlSqlUtil.updateSetColumns(columns, null, true, false);
        StringBuilder sb = new StringBuilder();
        sb.append(SqlUtil.updateTable(entityTable.getName(), null));
        sb.append(s);
        sb.append(XmlSqlUtil.whereClause(null));
        return sb.toString();
    }
}
