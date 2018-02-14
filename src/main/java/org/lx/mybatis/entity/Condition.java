package org.lx.mybatis.entity;

import org.lx.mybatis.helper.EntityHelper;

import java.util.*;


public class Condition {

    protected boolean distinct;

    protected boolean forUpdate;

    protected Set<String> selectColumns;//查询字段

    protected Set<String> excludeColumns;//排除的查询字段

    protected String countColumn;

    protected List<Criteria> oredCriteria = new ArrayList<>();

    protected Class<?> entityClass;

    protected EntityTable table;

    protected Map<String, EntityColumn> propertyMap;//属性和列对应

    protected String tableName;//动态表名

    protected List<OrderBy> orderBys = new ArrayList<>();

    public Condition(Class<?> entityClass) {
        this.entityClass = entityClass;
        table = EntityHelper.getEntityTable(entityClass);
        propertyMap = table.getPropertyMap();
    }

    public Condition orderBy(String property) {
        orderBy(property, false);
        return this;
    }

    public Condition orderBy(String property, Boolean descending) {
        this.orderBys.add(new OrderBy(property, descending));
        return this;
    }

    /**
     * 排除查询字段，优先级低于 selectProperties
     *
     * @param properties 属性名的可变参数
     * @return
     */
    public Condition excludeProperties(String... properties) {
        if (properties != null && properties.length > 0) {
            if (this.excludeColumns == null) {
                this.excludeColumns = new LinkedHashSet<>();
            }
            for (String property : properties) {
                if (propertyMap.containsKey(property)) {
                    this.excludeColumns.add(propertyMap.get(property).getColumn());
                } else {
                    // throw new MapperException("类 " + entityClass.getSimpleName() + " 不包含属性 \'" + property + "\'，或该属性被@Transient注释！");
                }
            }
        }
        return this;
    }

    /**
     * 指定要查询的属性列 - 这里会自动映射到表字段
     *
     * @param properties
     * @return
     */
    public Condition selectProperties(String... properties) {
        if (properties != null && properties.length > 0) {
            if (this.selectColumns == null) {
                this.selectColumns = new LinkedHashSet<String>();
            }
            for (String property : properties) {
                if (propertyMap.containsKey(property)) {
                    this.selectColumns.add(propertyMap.get(property).getColumn());
                } else {
                    // throw new MapperException("类 " + entityClass.getSimpleName() + " 不包含属性 \'" + property + "\'，或该属性被@Transient注释！");
                }
            }
        }
        return this;
    }

    public void or(Criteria criteria) {
        criteria.setAndOr("or");
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        criteria.setAndOr("or");
        oredCriteria.add(criteria);
        return criteria;
    }

    public void and(Criteria criteria) {
        criteria.setAndOr("and");
        oredCriteria.add(criteria);
    }

    public Criteria and() {
        Criteria criteria = createCriteriaInternal();
        criteria.setAndOr("and");
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            criteria.setAndOr("and");
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria(propertyMap);
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        distinct = false;
    }


    public String getCountColumn() {
        return countColumn;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }


    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public Set<String> getSelectColumns() {
        if (selectColumns != null && selectColumns.size() > 0) {
            //不需要处理
        } else if (excludeColumns != null && excludeColumns.size() > 0) {
            Collection<EntityColumn> entityColumns = propertyMap.values();
            selectColumns = new LinkedHashSet<String>(entityColumns.size() - excludeColumns.size());
            for (EntityColumn column : entityColumns) {
                if (!excludeColumns.contains(column.getColumn())) {
                    selectColumns.add(column.getColumn());
                }
            }
        }
        return selectColumns;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isForUpdate() {
        return forUpdate;
    }

    public void setForUpdate(boolean forUpdate) {
        this.forUpdate = forUpdate;
    }

    /**
     * 指定 count(property) 查询属性
     *
     * @param property
     */
    public void setCountProperty(String property) {
        if (propertyMap.containsKey(property)) {
            this.countColumn = propertyMap.get(property).getColumn();
        }
    }

    /**
     * 设置表名
     *
     * @param tableName
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}