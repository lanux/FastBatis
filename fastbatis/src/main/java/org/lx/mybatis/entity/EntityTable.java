package org.lx.mybatis.entity;

import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.lx.mybatis.FastMapperException;
import org.lx.mybatis.helper.SqlUtil;
import org.lx.mybatis.util.StringUtil;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 数据库表
 */
public class EntityTable {
    public static final Pattern DELIMITER = Pattern.compile("^[`\\[\"]?(.*?)[`\\]\"]?$");
    protected Map<String, TableColumn> propertyMap;
    private String name;
    private String catalog = "";
    private String schema = "";
    private List<TableColumn> columns;

    private Class<?> entityClass;

    public EntityTable(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * 生成当前实体的resultMapping列表
     *
     * @param configuration
     * @return
     */
    public List<ResultMapping> getResultMappings(Configuration configuration) {
        if (columns == null || columns.size() == 0) {
            return null;
        }
        List<ResultMapping> resultMappings = new ArrayList<ResultMapping>();
        for (TableColumn tableColumn : columns) {
            String column = tableColumn.getColumn();
            //去掉可能存在的分隔符
            Matcher matcher = DELIMITER.matcher(column);
            if (matcher.find()) {
                column = matcher.group(1);
            }
            ResultMapping.Builder builder = new ResultMapping.Builder(configuration, tableColumn.getProperty(), column, tableColumn.getJavaType());
            if (tableColumn.getJdbcType() != null) {
                builder.jdbcType(tableColumn.getJdbcType());
            }
            if (tableColumn.getTypeHandler() != null) {
                try {
                    builder.typeHandler(getInstance(tableColumn.getJavaType(), tableColumn.getTypeHandler()));
                } catch (Exception e) {
                    throw new FastMapperException(e);
                }
            }
            List<ResultFlag> flags = new ArrayList<ResultFlag>();
            if (tableColumn.isId()) {
                flags.add(ResultFlag.ID);
            }
            builder.flags(flags);
            resultMappings.add(builder.build());
        }

        return resultMappings;
    }

    /**
     * 初始化 - Condition 会使用
     */
    public void initPropertyMap() {
        propertyMap = new HashMap<String, TableColumn>(getColumns().size());
        for (TableColumn column : getColumns()) {
            propertyMap.put(column.getProperty(), column);
        }
    }

    /**
     * 实例化TypeHandler
     *
     * @param javaTypeClass
     * @param typeHandlerClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> TypeHandler<T> getInstance(Class<?> javaTypeClass, Class<?> typeHandlerClass) {
        if (javaTypeClass != null) {
            try {
                Constructor<?> c = typeHandlerClass.getConstructor(Class.class);
                return (TypeHandler<T>) c.newInstance(javaTypeClass);
            } catch (NoSuchMethodException ignored) {
                // ignored
            } catch (Exception e) {
                throw new TypeException("Failed invoking constructor for handler " + typeHandlerClass, e);
            }
        }
        try {
            Constructor<?> c = typeHandlerClass.getConstructor();
            return (TypeHandler<T>) c.newInstance();
        } catch (Exception e) {
            throw new TypeException("Unable to find a usable constructor for " + typeHandlerClass, e);
        }
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public List<TableColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<TableColumn> columns) {
        this.columns = columns;
    }

    public List<TableColumn> getKeyColumns() {
        return columns.stream().filter(TableColumn::isId).collect(Collectors.toList());
    }


    public String getKeyColumnNames() {
        return columns.stream().filter(TableColumn::isId).map(p -> p.getColumn()).collect(Collectors.joining(SqlUtil.COLUMN_JOIN_DELIMITER));
    }

    /**
     * 逗号分隔
     *
     * @return
     */
    public String getKeyProperties() {
        return columns.stream().filter(TableColumn::isId).map(p -> p.getProperty()).collect(Collectors.joining(SqlUtil.COLUMN_JOIN_DELIMITER));
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        if (StringUtil.isNotEmpty(catalog)) {
            return catalog;
        }
        if (StringUtil.isNotEmpty(schema)) {
            return schema;
        }
        return "";
    }

    public Map<String, TableColumn> getPropertyMap() {
        return propertyMap;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

}
