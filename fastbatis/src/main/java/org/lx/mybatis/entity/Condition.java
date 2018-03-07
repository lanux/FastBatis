package org.lx.mybatis.entity;

import org.lx.mybatis.helper.EntityHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Condition{

    protected List<Criteria> oredCriteria = new ArrayList<>();

    protected Class<?> entityClass;

    protected EntityTable table;

    protected Map<String, EntityColumn> propertyMap;//属性和列对应

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
    }


    public Class<?> getEntityClass() {
        return entityClass;
    }


    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

}