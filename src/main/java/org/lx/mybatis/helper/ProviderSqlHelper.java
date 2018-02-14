package org.lx.mybatis.helper;

import org.lx.mybatis.entity.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;
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
        Set<EntityColumn> columnList = table.getEntityClassColumns();
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
