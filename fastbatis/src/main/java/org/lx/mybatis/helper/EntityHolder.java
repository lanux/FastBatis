package org.lx.mybatis.helper;

import org.apache.ibatis.scripting.xmltags.ExpressionEvaluator;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.UnknownTypeHandler;
import org.lx.mybatis.annotation.Column;
import org.lx.mybatis.annotation.Entity;
import org.lx.mybatis.entity.EntityColumn;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.util.StringUtil;

import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EntityHolder {

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
            synchronized (entityTableMap) {
                entityTableMap.put(entityClass, entityTable = resolveEntity(entityClass));
            }
        }
        return entityTable;
    }

    /**
     * 获取全部列
     *
     * @param entityClass
     * @return
     */
    public static List<EntityColumn> getColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getEntityClassColumns();
    }

    /**
     * 获取主键信息
     *
     * @param entityClass
     * @return
     */
    public static List<EntityColumn> getPKColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getEntityClassPKColumns();
    }

    public static List<EntityColumn> filterNotNull(Collection<EntityColumn> collection, Object object) {
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        return collection.stream().filter(p -> evaluator.evaluateBoolean(p.getProperty() + " != null", object)).collect(Collectors.toList());
    }

    public static EntityTable resolveEntity(Class<?> entityClass) {
        //创建并缓存EntityTable
        EntityTable entityTable = null;
        if (entityClass.isAnnotationPresent(Entity.class)) {
            Entity table = entityClass.getAnnotation(Entity.class);
            if (StringUtil.isNotBlank(table.tableName())) {
                entityTable = new EntityTable(entityClass);
                entityTable.setName(table.tableName().trim());
            }
            if (StringUtil.isNotBlank(table.catalog())) {
                entityTable.setCatalog(table.catalog().trim());
            }
            if (StringUtil.isNotBlank(table.schema())) {
                entityTable.setSchema(table.schema().trim());
            }
        }
        if (entityTable == null) {
            entityTable = new EntityTable(entityClass);
            //驼峰转下划线
            entityTable.setName(StringUtil.camelhumpToUnderline(entityClass.getSimpleName()));
        }
        entityTable.setEntityClassColumns(new LinkedList<>());
        entityTable.setEntityClassPKColumns(new LinkedList<>());
        //处理所有列
        for (Field field : entityClass.getDeclaredFields()) {
            processField(entityTable, field);
        }
        //当pk.size=0的时候使用所有列作为主键
        if (entityTable.getEntityClassPKColumns().size() == 0) {
            entityTable.setEntityClassPKColumns(entityTable.getEntityClassColumns());
        }
        entityTable.initPropertyMap();
        entityTableMap.put(entityClass, entityTable);
        return entityTable;
    }

    protected static void processField(EntityTable entityTable, Field field) {
        //排除字段
        if (Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers()) || field.isAnnotationPresent(Transient.class)) {
            return;
        }
        EntityColumn entityColumn = new EntityColumn(entityTable);
        String columnName = null;
        if (field.isAnnotationPresent(Column.class)) {
            Column columnType = field.getAnnotation(Column.class);
            if (columnType.id()) {
                entityColumn.setId(true);
            }
            entityColumn.setBlob(columnType.isBlob());
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
        entityTable.getEntityClassColumns().add(entityColumn);
        if (entityColumn.isId()) {
            entityTable.getEntityClassPKColumns().add(entityColumn);
        }
    }


    public static List<EntityColumn> getColumns(List<EntityColumn> columnList, boolean excludeBlob, boolean excludeUnInsertable, boolean excludeUnUpdatable) {
        if (columnList != null) {
            return columnList.stream().filter(p -> excludeBlob ? p.isBlob() : true)
                    .filter(p -> excludeUnInsertable ? p.isInsertable() : true)
                    .filter(p -> excludeUnUpdatable ? p.isUpdatable() : true)
                    .collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }
}