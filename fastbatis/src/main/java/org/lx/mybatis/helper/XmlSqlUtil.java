package org.lx.mybatis.helper;

import org.lx.mybatis.entity.TableColumn;
import org.lx.mybatis.util.StringUtil;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class XmlSqlUtil {

    /**
     * 判断自动!=null的条件结构
     *
     * @param aliasName
     * @param column
     * @param contents
     * @return
     */
    public static String getIfNotNull(String aliasName, TableColumn column, String contents) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        if (StringUtil.isNotEmpty(aliasName)) {
            sql.append(aliasName).append(".");
        }
        sql.append(column.getProperty()).append(" != null");
        sql.append("\">\n");
        sql.append(contents);
        sql.append("\n</if>\n");
        return sql.toString();
    }

    /**
     * @param aliasName
     * @param column
     * @param contents
     * @return
     */
    public static String getIfNotEmpty(String aliasName, TableColumn column, String contents) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        if (StringUtil.isNotEmpty(aliasName)) {
            sql.append(aliasName).append(".");
        }
        sql.append(column.getProperty()).append(" != null");
        if (column.getJavaType().equals(String.class)) {
            sql.append(" and ");
            if (StringUtil.isNotEmpty(aliasName)) {
                sql.append(aliasName).append(".");
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
    public static String whereAllIfColumns(String aliasName, List<TableColumn> columns) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>\n");
        for (TableColumn column : columns) {
            sql.append(getIfNotNull(aliasName, column, "AND " + column.getColumnEqualsHolder()));
        }
        sql.append("</where>\n");
        return sql.toString();
    }

    public static String getAllIfColumns(String aliasName, Collection<TableColumn> columnList) {
        return columnList.stream()
                .map(entityColumn -> getIfNotNull(aliasName, entityColumn, entityColumn.getColumn()))
                .collect(Collectors.joining(SqlUtil.COLUMN_JOIN_DELIMITER));
    }

    public static String getAllIfColumnValueHolder(String aliasName, Collection<TableColumn> columnList) {
        return columnList.stream()
                .map(entityColumn -> getIfNotNull(aliasName, entityColumn, entityColumn.getColumnHolder(aliasName)))
                .collect(Collectors.joining(SqlUtil.COLUMN_JOIN_DELIMITER));
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
    public static String updateSetColumns(List<TableColumn> columns, String entityName, boolean notNull, boolean notEmpty) {
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


    /**
     *  返回: VALUES <foreach> </foreach>
     * @param columnList
     * @return
     */
    public static String batchInsertValues(List<TableColumn> columnList) {
        StringBuilder sql = new StringBuilder(" VALUES ");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        for (TableColumn column : columnList) {
            if (!column.isId() && column.isInsertable()) {
                sql.append(column.getColumnHolder("record") + ",");
            }
        }
        sql.append("</trim>");
        sql.append("</foreach>");
        return sql.toString();
    }


    public static String whereClause(String paramName) {
        paramName = StringUtil.isNotBlank(paramName)?paramName:"_parameter";
        return "\n<if test=\""+paramName+" != null\">" +
                "<where>\n" +
                "  <foreach collection=\"_parameter.oredCriteria\" item=\"criteria\">\n" +
                "    <if test=\"criteria.criteria!=null and criteria.criteria.size > 0\">\n" +
                "      ${criteria.andOr}" +
                "      <trim prefix=\"(\" prefixOverrides=\"and | or \" suffix=\")\">\n" +
                "        <foreach collection=\"criteria.criteria\" item=\"criterion\">\n" +
                "          <choose>\n" +
                "            <when test=\"criterion.noValue\">\n" +
                "             ${criterion.andOr} ${criterion.column} ${criterion.condition}\n" +
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
