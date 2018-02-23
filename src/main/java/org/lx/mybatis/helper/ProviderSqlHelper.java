package org.lx.mybatis.helper;

import org.lx.mybatis.entity.*;
import org.lx.mybatis.util.StringUtil;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ProviderSqlHelper {

    private static final CharSequence COLUMN_JOIN_DELIMITER = ",";

    /**
     * 返回：#{id,jdbcType=NUMERIC},#{name,jdbcType=VARCHAR,typeHandler=MyTypeHandler}
     *
     * @param columnList
     * @return
     */
    public static String getValuesHolder(Collection<EntityColumn> columnList) {
        return columnList.stream().map(EntityColumn::getColumnHolder).collect(Collectors.joining(COLUMN_JOIN_DELIMITER));
    }

    public static String[] getEqualsHolder(Collection<EntityColumn> columnList) {
        return columnList.stream().map(EntityColumn::getColumnEqualsHolder).toArray(String[]::new);
    }


    /**
     * 获取所有查询列，如id,name,code...
     *
     * @param entityClass
     * @return
     */
    public static String getAllColumns(Class<?> entityClass) {
        return getAllColumns(EntityHelper.getEntityTable(entityClass));
    }

    /**
     * 获取所有查询列，如id,name,code...
     *
     * @param table
     * @return
     */
    public static String getAllColumns(EntityTable table) {
        List<EntityColumn> columnList = table.getEntityClassColumns();
        return getColumns(columnList);
    }

    /**
     * 获取所有查询列，如id,name,code...
     *
     * @param columnList
     * @return
     */
    public static String getColumns(Collection<EntityColumn> columnList) {
        return columnList.stream().map(EntityColumn::getColumn).collect(Collectors.joining(COLUMN_JOIN_DELIMITER));
    }

    /**
     * 返回格式如:colum = #{age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
     *
     * @param condition
     * @return
     */
    public static String whereClause(Condition condition) {
        if (condition == null) return null;
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = condition.getOredCriteria();
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
                    sb.append(String.format("%s #{oredCriteria[%d].criteria[%d].value}", criterion.columnCondition(), i, j));
                } else if (criterion.isBetweenValue()) {
                    sb.append(String.format("%s #{oredCriteria[%d].criteria[%d].value} AND #{criteria.andList[%d].data[%d].secondValue}", criterion.columnCondition(), i, j, i, j));
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

    /**
     * 判断自动!=null的条件结构
     *
     * @param entityName
     * @param column
     * @param contents
     * @return
     */
    public static String getIfNotNull(String entityName, EntityColumn column, String contents) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        if (StringUtil.isNotEmpty(entityName)) {
            sql.append(entityName).append(".");
        }
        sql.append(column.getProperty()).append(" != null");
        sql.append("\">\n");
        sql.append(contents);
        sql.append("\n</if>\n");
        return sql.toString();
    }

    /**
     * @param entityName
     * @param column
     * @param contents
     * @return
     */
    public static String getIfNotEmpty(String entityName, EntityColumn column, String contents) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        if (StringUtil.isNotEmpty(entityName)) {
            sql.append(entityName).append(".");
        }
        sql.append(column.getProperty()).append(" != null");
        if (column.getJavaType().equals(String.class)) {
            sql.append(" and ");
            if (StringUtil.isNotEmpty(entityName)) {
                sql.append(entityName).append(".");
            }
            sql.append(column.getProperty()).append(" != '' ");
        }
        sql.append("\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * where所有列的条件，会判断是否!=null<br/>
     * <where>
     * <if test="state != null">
     * AND state = #{state}
     * </if><p/>
     * <if test="title != null">
     * AND title = #{title}
     * </if><p/>
     * <if test="author != null and author.name != null">
     * AND author_name like #{author.name}
     * </if>
     * </where>
     *
     * @param columns
     * @return
     */
    public static String whereAllIfColumns(String entityName, List<EntityColumn> columns) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>\n");
        for (EntityColumn column : columns) {
            sql.append(getIfNotNull(entityName, column, "AND " + column.getColumnEqualsHolder()));
        }
        sql.append("</where>\n");
        return sql.toString();
    }

    public static String getAllIfColumns(String entityName,Collection<EntityColumn> columnList) {
        return columnList.stream().map(entityColumn -> getIfNotNull(null, entityColumn, entityColumn.getColumn())).collect(Collectors.joining(COLUMN_JOIN_DELIMITER));
    }

    public static String getAllIfColumnValueHolder(String entityName,Collection<EntityColumn> columnList) {
        return columnList.stream().map(entityColumn -> getIfNotNull(entityName, entityColumn, entityColumn.getColumnHolder(entityName))).collect(Collectors.joining(COLUMN_JOIN_DELIMITER));
    }


    /**
     * update set列
     *
     * @param columns
     * @param entityName 实体映射名
     * @param notNull    判断!=null
     * @param notEmpty   判断!=null 且 String类型!=''
     * @return
     */
    public static String updateSetColumns(List<EntityColumn> columns, String entityName, boolean notNull, boolean notEmpty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<set>");
        columns.stream().filter(column -> !column.isId() && column.isUpdatable()).forEach(column -> {
            if (notEmpty) {
                sql.append(getIfNotEmpty(entityName, column, column.getColumnEqualsHolder(entityName) + ","));
            } else if (notNull) {
                sql.append(getIfNotNull(entityName, column, column.getColumnEqualsHolder(entityName) + ","));
            } else {
                sql.append(column.getColumnEqualsHolder(entityName) + ",");
            }
        });
        sql.append("</set>");
        return sql.toString();
    }

    public static String fromTable(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM ");
        sql.append(tableName);
        sql.append(" ");
        return sql.toString();
    }

    public static String updateTable(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(tableName);
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

    public static String batchInsertValues(List<EntityColumn> columnList) {
        StringBuilder sql = new StringBuilder(" VALUES ");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        for (EntityColumn column : columnList) {
            if (!column.isId() && column.isInsertable()) {
                sql.append(column.getColumnHolder("record") + ",");
            }
        }
        sql.append("</trim>");
        sql.append("</foreach>");
        return sql.toString();
    }


    public static String xmlWhereClause(Condition condition) {
        return "<if test=\"_parameter != null\">" +
                "<where>\n" +
                "  <foreach collection=\"oredCriteria\" item=\"criteria\">\n" +
                "    <if test=\"criteria.isNotEmpty\">\n" +
                "      ${criteria.andOr}" +
                "      <trim prefix=\"(\" prefixOverrides=\"and | or \" suffix=\")\">\n" +
                "        <foreach collection=\"criteria.criteria\" item=\"criterion\">\n" +
                "          <choose>\n" +
                "            <when test=\"criterion.noValue\">\n" +
                "             ${criterion.andOr} ${criterion.column}  ${criterion.condition}\n" +
                "            </when>\n" +
                "            <when test=\"criterion.singleValue\">\n" +
                "             ${criterion.andOr} ${criterion.column} ${criterion.condition} #{criterion.value}\n" +
                "            </when>\n" +
                "            <when test=\"criterion.betweenValue\">\n" +
                "              ${criterion.andOr} ${criterion.column} ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n" +
                "            </when>\n" +
                "            <when test=\"criterion.listValue\">\n" +
                "              ${criterion.andOr} ${criterion.column} ${criterion.condition}\n" +
                "              <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">\n" +
                "                #{listItem}\n" +
                "              </foreach>\n" +
                "            </when>\n" +
                "          </choose>\n" +
                "        </foreach>\n" +
                "      </trim>\n" +
                "    </if>\n" +
                "  </foreach>\n" +
                "</where>" +
                "</if>";
    }

}
