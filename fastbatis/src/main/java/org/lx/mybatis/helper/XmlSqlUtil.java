package org.lx.mybatis.helper;

import org.lx.mybatis.entity.Operator;
import org.lx.mybatis.entity.TableColumn;
import org.lx.mybatis.entity.ValueMode;
import org.lx.mybatis.util.StringUtil;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class XmlSqlUtil {
    /**
     * 返回格式如: <br>
     * ${[aliasName.]column.property}+separator
     *
     * @param aliasName
     * @param column
     * @param separator
     * @return
     */
    public static String get$ValueHolder(TableColumn column, String aliasName, String separator) {
        StringBuilder sb = new StringBuilder(ValueMode.DOLLAR.getValue());
        sb.append("{");
        if (StringUtil.isNotBlank(aliasName)) {
            sb.append(aliasName);
            sb.append(".");
        }
        sb.append(column.getProperty());
        sb.append("}");
        if (StringUtil.isNotBlank(separator)) {
            sb.append(separator);
        }
        return sb.toString();
    }

    /**
     * 返回格式如: <br>
     * #{[aliasName.]column.property ,jdbcType=NUMERIC,typeHandler=MyTypeHandler}+separator
     *
     * @param aliasName
     * @param column
     * @param separator
     * @return
     */
    public static String getValueHolder(TableColumn column, String aliasName, String separator) {
        StringBuilder sb = new StringBuilder("#{");
        if (StringUtil.isNotBlank(aliasName)) {
            sb.append(aliasName);
            sb.append(".");
        }
        sb.append(column.getProperty());
        //如果字段被设值为null则jdbcType是需要的
        if (column.getJdbcType() != null) {
            sb.append(",jdbcType=");
            sb.append(column.getJdbcType().toString());
        } else if (column.getTypeHandler() != null) {
            sb.append(",typeHandler=");
            sb.append(column.getTypeHandler());
        }
        sb.append("}");
        if (StringUtil.isNotBlank(separator)) {
            sb.append(separator);
        }
        return sb.toString();
    }

    public static String getColumnEqualsValueHolder(TableColumn column, String aliasName, String separator) {
        StringBuilder sb = new StringBuilder(column.getColumn());
        sb.append(Operator.EQ);
        sb.append(getValueHolder(column, aliasName, separator));
        return sb.toString();
    }

    /**
     * 判断自动!=null的条件结构<br/>
     * eg:<br/>
     * &lt;if test="[aliasName.]column.property != null" &gt; <br/>
     * contents <br/>
     * &lt;/if&gt; <br/>
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
        sql.append("\">");
        sql.append(SqlUtil.NEW_LINE_DELIMITER);
        sql.append(contents);
        sql.append(SqlUtil.NEW_LINE_DELIMITER);
        sql.append("</if>");
        return sql.toString();
    }

    /**
     *
     * <if test="[aliasName.]column.property == null" >
     *  column.defaultValue
     * </if>
     * @param column
     * @param aliasName
     * @param separator
     * @return
     */
    public static String getIfDefaultValue(TableColumn column,String defaultValue,String aliasName, String separator) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        if (StringUtil.isNotEmpty(aliasName)) {
            sql.append(aliasName).append(".");
        }
        sql.append(column.getProperty()).append(" == null");
        sql.append("\">");
        sql.append(SqlUtil.NEW_LINE_DELIMITER);
        sql.append(defaultValue);
        sql.append(separator);
        sql.append(SqlUtil.NEW_LINE_DELIMITER);
        sql.append("</if>");
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
        sql.append(SqlUtil.NEW_LINE_DELIMITER);
        sql.append(contents);
        sql.append(SqlUtil.NEW_LINE_DELIMITER);
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * where所有列的条件，会判断是否!=null<br/>
     * <pre>
     * <where>
     *  <if test="state != null">
     *      AND state = #{state}
     *  </if>
     * <if test="title != null">
     *      AND title = #{title}
     *  </if>
     * </where>
     * </pre>
     *
     * @param columns
     * @return
     */
    public static String whereAllIfColumns(String aliasName, List<TableColumn> columns) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        sql.append(SqlUtil.NEW_LINE_DELIMITER);
        for (TableColumn column : columns) {
            sql.append(getIfNotNull(aliasName, column, "AND " + getColumnEqualsValueHolder(column, aliasName, null)));
            sql.append(SqlUtil.NEW_LINE_DELIMITER);
        }
        sql.append("</where>");
        return sql.toString();
    }

    public static String getAllIfColumns(String aliasName, Collection<TableColumn> columnList) {
        return columnList.stream().map(entityColumn -> getIfNotNull(aliasName, entityColumn,
                entityColumn.getColumn() + SqlUtil.COLUMN_JOIN_DELIMITER))
                .collect(Collectors.joining(SqlUtil.NEW_LINE_DELIMITER));
    }

    public static String getAllIfColumnValueHolder(String aliasName, Collection<TableColumn> columnList) {
        return columnList.stream().map(entityColumn -> getIfNotNull(aliasName, entityColumn,
                getValueHolder(entityColumn, aliasName, SqlUtil.COLUMN_JOIN_DELIMITER)))
                .collect(Collectors.joining(SqlUtil.NEW_LINE_DELIMITER));
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
    public static String updateSetColumns(List<TableColumn> columns, String entityName, boolean notNull,
            boolean notEmpty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<set>");
        columns.stream().filter(column -> !column.isId() && column.isUpdateAble()).forEach(column -> {
            if (notEmpty) {
                sql.append(getIfNotEmpty(entityName, column, getColumnEqualsValueHolder(column,entityName,SqlUtil.COLUMN_JOIN_DELIMITER)));
            } else if (notNull) {
                sql.append(getIfNotNull(entityName, column, getColumnEqualsValueHolder(column,entityName,SqlUtil.COLUMN_JOIN_DELIMITER)));
            } else {
                sql.append(getColumnEqualsValueHolder(column,entityName,SqlUtil.COLUMN_JOIN_DELIMITER));
            }
        });
        sql.append("</set>");
        return sql.toString();
    }

    /**
     * 返回: VALUES <foreach> </foreach>
     *
     * @param columnList
     * @return
     */
    public static String batchInsertValues(List<TableColumn> columnList) {
        StringBuilder sql = new StringBuilder(" VALUES ");
        sql.append(SqlUtil.NEW_LINE_DELIMITER);
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
        sql.append(SqlUtil.NEW_LINE_DELIMITER);
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        for (TableColumn column : columnList) {
            if (!column.isId() && column.isInsertAble()) {
                sql.append(getValueHolder(column,"record",SqlUtil.COLUMN_JOIN_DELIMITER));
                sql.append(SqlUtil.NEW_LINE_DELIMITER);
            }
        }
        sql.append("</trim>");
        sql.append(SqlUtil.NEW_LINE_DELIMITER);
        sql.append("</foreach>");
        return sql.toString();
    }

    public static String whereClause(String paramName) {
        paramName = StringUtil.isNotBlank(paramName) ? paramName : "_parameter";
        return "\n<if test=\"" + paramName + " != null\">" + "<where>\n"
                + "  <foreach collection=\"_parameter.oredCriteria\" item=\"criteria\">\n"
                + "    <if test=\"criteria.criteria!=null and criteria.criteria.size > 0\">\n"
                + "      ${criteria.andOr}" + "      <trim prefix=\"(\" prefixOverrides=\"and | or \" suffix=\")\">\n"
                + "        <foreach collection=\"criteria.criteria\" item=\"criterion\">\n" + "          <choose>\n"
                + "            <when test=\"criterion.noValue\">\n"
                + "             ${criterion.andOr} ${criterion.column} ${criterion.condition}\n"
                + "            </when>\n" + "            <when test=\"criterion.singleValue\">\n"
                + "             ${criterion.andOr} ${criterion.column} ${criterion.condition} #{criterion.value}\n"
                + "            </when>\n" + "            <when test=\"criterion.betweenValue\">\n"
                + "              ${criterion.andOr} ${criterion.column} ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n"
                + "            </when>\n" + "            <when test=\"criterion.listValue\">\n"
                + "              ${criterion.andOr} ${criterion.column} ${criterion.condition}\n"
                + "              <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">\n"
                + "                #{listItem}\n" + "              </foreach>\n" + "            </when>\n"
                + "          </choose>\n" + "        </foreach>\n" + "      </trim>\n" + "    </if>\n"
                + "  </foreach>\n" + "</where>" + "</if>";
    }

}
