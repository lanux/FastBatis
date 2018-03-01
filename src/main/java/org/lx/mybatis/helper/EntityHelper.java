package org.lx.mybatis.helper;

import org.apache.ibatis.scripting.xmltags.ExpressionEvaluator;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.UnknownTypeHandler;
import org.lx.mybatis.MapperException;
import org.lx.mybatis.entity.EntityColumn;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.util.StringUtil;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EntityHelper {

    /**
     * 实体类 => 表对象
     */
    private static final Map<Class<?>, EntityTable> entityTableMap = new ConcurrentHashMap<Class<?>, EntityTable>();

    /**
     * 获取表对象
     *
     * @param entityClass
     * @return
     */
    public static EntityTable getEntityTable(Class<?> entityClass) {
        EntityTable entityTable = entityTableMap.get(entityClass);
        if (entityTable == null) {
            throw new MapperException("无法获取实体类" + entityClass.getCanonicalName() + "对应的表名!");
        }
        return entityTable;
    }

    /**
     * 获取全部列
     *
     * @param entityClass
     * @return
     */
    public static Set<EntityColumn> getColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getEntityClassColumns();
    }

    /**
     * 获取主键信息
     *
     * @param entityClass
     * @return
     */
    public static Set<EntityColumn> getPKColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getEntityClassPKColumns();
    }

    /**
     * 获取查询的Select
     *
     * @param entityClass
     * @return
     */
    public static String getSelectColumns(Class<?> entityClass) {
        EntityTable entityTable = getEntityTable(entityClass);
        if (entityTable.getBaseSelect() != null) {
            return entityTable.getBaseSelect();
        }
        Set<EntityColumn> columnList = getColumns(entityClass);
        StringBuilder selectBuilder = new StringBuilder();
        boolean skipAlias = Map.class.isAssignableFrom(entityClass);
        for (EntityColumn entityColumn : columnList) {
            selectBuilder.append(entityColumn.getColumn());
            if (!skipAlias && !entityColumn.getColumn().equalsIgnoreCase(entityColumn.getProperty())) {
                //不等的时候分几种情况，例如`DESC`
                if (entityColumn.getColumn().substring(1, entityColumn.getColumn().length() - 1).equalsIgnoreCase(entityColumn.getProperty())) {
                    selectBuilder.append(",");
                } else {
                    selectBuilder.append(" AS ").append(entityColumn.getProperty()).append(",");
                }
            } else {
                selectBuilder.append(",");
            }
        }
        entityTable.setBaseSelect(selectBuilder.substring(0, selectBuilder.length() - 1));
        return entityTable.getBaseSelect();
    }

    public static List<EntityColumn> filterNotNull(Collection<EntityColumn> collection, Object object) {
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        return collection.stream().filter(p -> evaluator.evaluateBoolean(p.getProperty() + " != null", object)).collect(Collectors.toList());
    }

    public static EntityTable resolveEntity(Class<?> entityClass) {
        //创建并缓存EntityTable
        EntityTable entityTable = null;
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            if (!"".equals(table.name())) {
                entityTable = new EntityTable(entityClass);
                entityTable.setTable(table);
            }
            if (!"".equals(table.catalog())) {
                entityTable.setCatalog(table.catalog());
            }
            if (!"".equals(table.schema())) {
                entityTable.setSchema(table.schema());
            }
        }
        if (entityTable == null) {
            entityTable = new EntityTable(entityClass);
            //驼峰转下划线
            entityTable.setName(StringUtil.camelhumpToUnderline(entityClass.getSimpleName()));
        }
        entityTable.setEntityClassColumns(new LinkedHashSet<EntityColumn>());
        entityTable.setEntityClassPKColumns(new LinkedHashSet<EntityColumn>());
        //处理所有列
        for (Field field : entityClass.getDeclaredFields()) {
            processField(entityTable, field);
        }
        //当pk.size=0的时候使用所有列作为主键
        if (entityTable.getEntityClassPKColumns().size() == 0) {
            entityTable.setEntityClassPKColumns(entityTable.getEntityClassColumns());
        }
        entityTable.initPropertyMap();
        return entityTable;
    }

    protected static void processField(EntityTable entityTable, Field field) {
        //排除字段
        if (Modifier.isTransient(field.getModifiers()) || field.isAnnotationPresent(Transient.class) || field.isAnnotationPresent(javax.persistence.Transient.class)) {
            return;
        }
        EntityColumn entityColumn = new EntityColumn(entityTable);
//        entityColumn.setUseJavaType(config.isUseJavaType());
        if (field.isAnnotationPresent(Id.class)) {
            entityColumn.setId(true);
        }
        String columnName = null;
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            columnName = column.name();
        }
        if (field.isAnnotationPresent(org.lx.mybatis.annotation.Column.class)) {
            org.lx.mybatis.annotation.Column columnType = field.getAnnotation(org.lx.mybatis.annotation.Column.class);
            //是否为 blob 字段
            entityColumn.setBlob(columnType.isBlob());
            //column可以起到别名的作用
            if (StringUtil.isEmpty(columnName) && StringUtil.isNotEmpty(columnType.name())) {
                columnName = columnType.name();
            }
            if (columnType.jdbcType() != JdbcType.UNDEFINED) {
                entityColumn.setJdbcType(columnType.jdbcType());
            }
            if (columnType.typeHandler() != UnknownTypeHandler.class) {
                entityColumn.setTypeHandler(columnType.typeHandler());
            }
        }
        //列名
        if (StringUtil.isEmpty(columnName)) {
            columnName = StringUtil.camelhumpToUnderline(field.getName());
        }
        //自动处理关键字
//        if (SqlReservedWords.containsWord(columnName)) {
//            columnName = MessageFormat.format(config.getWrapKeyword(), columnName);
//        }
        entityColumn.setProperty(field.getName());
        entityColumn.setColumn(columnName);
        entityColumn.setJavaType(field.getType());
        if (field.getType().isPrimitive()) {
//            logger.warn("通用 Mapper 警告信息: <[" + entityColumn + "]> 使用了基本类型，基本类型在动态 SQL 中由于存在默认值，因此任何时候都不等于 null，建议修改基本类型为对应的包装类型!");
        }
//        //OrderBy
//        processOrderBy(entityTable, field, entityColumn);
//        //处理主键策略
//        processKeyGenerator(entityTable, field, entityColumn);
        entityTable.getEntityClassColumns().add(entityColumn);
        if (entityColumn.isId()) {
            entityTable.getEntityClassPKColumns().add(entityColumn);
        }
    }


}