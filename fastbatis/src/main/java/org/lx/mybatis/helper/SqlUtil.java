package org.lx.mybatis.helper;

import org.lx.mybatis.entity.Condition;
import org.lx.mybatis.entity.Criterion;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.entity.TableColumn;
import org.lx.mybatis.util.StringUtil;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SqlUtil {

    public static final String COLUMN_JOIN_DELIMITER = ",";
    public static final String NEW_LINE_DELIMITER = "\n";

    /**
     * 返回格式如:colum = #{age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
     *
     * @param condition
     * @return
     */
    public static String whereClause(Condition condition) {
        if (condition == null)
            return null;
        StringBuilder sb = new StringBuilder();
        List<Condition.Criteria> oredCriteria = condition.getOredCriteria();
        for (int i = 0; i < oredCriteria.size(); i++) {
            if (oredCriteria.size() > 1) {
                sb.append("(");
            }
            List<Criterion> criteria = oredCriteria.get(i).getCriteria();
            for (int j = 0; j < criteria.size(); j++) {
                Criterion criterion = criteria.get(j);
                if (criterion.isNoValue()) {
                    sb.append(criterion.columnCondition());
                } else if (criterion.isSingleValue()) {
                    sb.append(String.format("%s #{oredCriteria[%d].criteria[%d].value}", criterion.columnCondition(), i,
                            j));
                } else if (criterion.isBetweenValue()) {
                    sb.append(String.format(
                            "%s #{oredCriteria[%d].criteria[%d].value} AND #{criteria.andList[%d].data[%d].secondValue}",
                            criterion.columnCondition(), i, j, i, j));
                } else if (criterion.isListValue()) {
                    sb.append(criterion.columnCondition() + " (");
                    List<?> listItems = (List<?>) criterion.getValue();
                    for (int k = 0; k < listItems.size(); k++) {
                        sb.append(String.format("#{oredCriteria[%d].criteria[%d].value[%d]}", i, j, k));
                        if (k < listItems.size() - 1) {
                            sb.append(",");
                        }
                    }
                    sb.append(")");
                }
                if (j < criteria.size() - 1) {
                    sb.append(" " + oredCriteria.get(i).getAndOr() + " ");
                }
            }
            if (oredCriteria.size() > 1) {
                sb.append(")");
            }
            if (i < oredCriteria.size() - 1) {
                sb.append(" OR ");
            }
        }
        return sb.toString();
    }

    public static String fromTable(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM ");
        sql.append(tableName);
        sql.append(" ");
        return sql.toString();
    }

    public static String updateTable(String tableName, String alias) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(tableName);
        if (StringUtil.isNotEmpty(alias)) {
            sql.append(" " + alias);
        }
        sql.append(" ");
        return sql.toString();
    }

    public static String deleteFromTable(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(tableName);
        sql.append(" ");
        return sql.toString();
    }

    public static String insertIntoTable(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(tableName);
        sql.append(" ");
        return sql.toString();
    }

    /**
     * 返回：#{id,jdbcType=NUMERIC},#{name,jdbcType=VARCHAR,typeHandler=MyTypeHandler}
     *
     * @param columnList
     * @return
     */
    public static String getValuesHolder(Collection<TableColumn> columnList) {
        return columnList.stream().map(p -> XmlSqlUtil.getValueHolder(p, null, null))
                .collect(Collectors.joining(SqlUtil.COLUMN_JOIN_DELIMITER));
    }

    /**
     * 返回:colum = #{age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
     *
     * @param columnList
     * @return
     */
    public static String[] getEqualsHolder(Collection<TableColumn> columnList) {
        return columnList.stream().map(p -> XmlSqlUtil.getColumnEqualsValueHolder(p, null, null))
                .toArray(String[]::new);
    }

    public static String getColumnNames(List<TableColumn> columnList, boolean excludeBlob, boolean excludeUnInsertAble,
            boolean excludeUpdateAble) {
        if (columnList != null) {
            return columnList.stream().filter(p -> excludeBlob ? p.isBlob() : true)
                    .filter(p -> excludeUnInsertAble ? p.isInsertAble(): true)
                    .filter(p -> excludeUpdateAble ? p.isUpdateAble() : true).map(TableColumn::getColumn)
                    .collect(Collectors.joining(SqlUtil.COLUMN_JOIN_DELIMITER));
        }
        return "";
    }

    public static String getColumnNames(List<TableColumn> columnList) {
        if (columnList != null) {
            return columnList.stream().map(TableColumn::getColumn)
                    .collect(Collectors.joining(SqlUtil.COLUMN_JOIN_DELIMITER));
        }
        return "";
    }

    /**
     * 获取所有查询列，如id,name,code...
     *
     * @param entityClass
     * @return
     */
    public static String getAllColumns(Class<?> entityClass) {
        return getAllColumns(EntityTables.getEntityTable(entityClass));
    }

    /**
     * 获取所有查询列，如id,name,code...
     *
     * @param table
     * @return
     */
    public static String getAllColumns(EntityTable table) {
        List<TableColumn> columnList = table.getColumns();
        return getColumnNames(columnList, false, false, false);
    }

}
