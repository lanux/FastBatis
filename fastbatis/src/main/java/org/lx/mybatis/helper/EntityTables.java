package org.lx.mybatis.helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.scripting.xmltags.ExpressionEvaluator;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.UnknownTypeHandler;
import org.lx.mybatis.annotation.Column;
import org.lx.mybatis.annotation.Entity;
import org.lx.mybatis.entity.EntityTable;
import org.lx.mybatis.entity.TableColumn;
import org.lx.mybatis.util.StringUtil;

import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EntityTables {

    private static final Log logger = LogFactory.getLog(EntityTables.class);


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
            entityTableMap.put(entityClass, entityTable = resolveEntity(entityClass));
        }
        return entityTable;
    }

    /**
     * 获取全部列
     *
     * @param entityClass
     * @return
     */
    public static List<TableColumn> getColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getColumns();
    }

    /**
     * 获取主键信息
     *
     * @param entityClass
     * @return
     */
    public static List<TableColumn> getPKColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getKeyColumns();
    }

    public static List<TableColumn> filterNotNull(Collection<TableColumn> collection, Object object) {
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
        entityTable.setColumns(new LinkedList<>());
        //处理所有列
        for (Field field : entityClass.getDeclaredFields()) {
            processField(entityTable, field);
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
        TableColumn tableColumn = new TableColumn();
        String columnName = null;
        if (field.isAnnotationPresent(Column.class)) {
            Column columnType = field.getAnnotation(Column.class);
            if (columnType.id()) {
                tableColumn.setId(true);
            }
            tableColumn.setBlob(columnType.isBlob());
            if (StringUtil.isBlank(columnName) && StringUtil.isNotBlank(columnType.name())) {
                columnName = columnType.name();
            }
            if (columnType.jdbcType() != JdbcType.UNDEFINED) {
                tableColumn.setJdbcType(columnType.jdbcType());
            }
            if (columnType.reserved()) {
                tableColumn.setColumn(columnType.delimiter() + columnName + columnType.delimiter());
            }
            if (StringUtil.isNotBlank(columnType.defaultValue())){
                tableColumn.setColumn(columnType.defaultValue());
            }
            tableColumn.setInsertAble(columnType.insertAble());
            tableColumn.setUpdateAble(columnType.updateAble());
        }
        //列名
        if (StringUtil.isBlank(columnName)) {
            columnName = StringUtil.camelhumpToUnderline(field.getName());
        }
        tableColumn.setProperty(field.getName());
        tableColumn.setColumn(columnName);
        tableColumn.setJavaType(field.getType());
        if (field.getType().isPrimitive()) {
            logger.error("错误信息: <[" + tableColumn + "]> 使用了基本类型，建议修改为对应的包装类型!");
        }
        entityTable.getColumns().add(tableColumn);
    }


    public static List<TableColumn> getColumns(List<TableColumn> columnList, boolean excludeBlob, boolean excludeUnInsertAble, boolean excludeUnUpdateAble) {
        if (columnList != null) {
            return columnList.stream().filter(p -> excludeBlob ? p.isBlob() : true)
                    .filter(p -> excludeUnInsertAble ? p.isInsertAble() : true)
                    .filter(p -> excludeUnUpdateAble ? p.isUpdateAble() : true)
                    .collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }
}