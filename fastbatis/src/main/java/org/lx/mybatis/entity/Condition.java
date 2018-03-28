package org.lx.mybatis.entity;

import org.lx.mybatis.helper.EntityHolder;
import org.lx.mybatis.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Condition {

    protected List<Criteria> oredCriteria = new ArrayList<>();

    protected String alias;

    protected Class<?> entityClass;

    protected EntityTable table;

    protected Map<String, EntityColumn> propertyMap;//属性和列对应

    public Condition(Class<?> entityClass) {
        this.entityClass = entityClass;
        table = EntityHolder.getEntityTable(entityClass);
        propertyMap = table.getPropertyMap();
    }

    public String getAlias() {
        return alias;
    }

    public Condition alias(String alias) {
        this.alias = alias;
        return this;
    }

    private Criteria or() {
        Criteria criteria = createCriteriaInternal();
        criteria.setAndOr("or");
        oredCriteria.add(criteria);
        return criteria;
    }

    private Criteria and() {
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

    public class Criteria {
        protected List<Criterion> criteria = new ArrayList<>();

        protected String andOr = "OR";//连接条件

        protected Criteria() {
            criteria = new ArrayList<>();
        }

        protected void addCriterion(Criterion criterion) {
            if (StringUtil.isNotEmpty(Condition.this.alias)) {
                criterion.setColumn(Condition.this.alias + "" + criterion.getColumn());
            }
            criteria.add(criterion);
        }


        protected void addCriterion(String column, String condition) {
            addCriterion(new Criterion(column, condition));
        }

        protected void addCriterion(String column, String condition, Object value) {
            addCriterion(new Criterion(column, condition, value));
        }

        protected void addCriterion(String column, String condition, Object value1, Object value2) {
            addCriterion(new Criterion(column, condition, value1, value2));
        }


        protected void addOrCriterion(String column, String condition) {
            addCriterion(new Criterion(column, condition, true));
        }

        protected void addOrCriterion(String column, String condition, Object value) {
            addCriterion(new Criterion(column, condition, value, true));
        }

        protected void addOrCriterion(String column, String condition, Object value1, Object value2) {
            addCriterion(new Criterion(column, condition, value1, value2, true));
        }

        public Criteria andIsNull(String property) {
            addCriterion(column(property), "is null");
            return this;
        }

        public Criteria andIsNotNull(String property) {
            addCriterion(column(property), "is not null");
            return this;
        }

        public Criteria andEqualTo(String property, Object value) {
            addCriterion(column(property), "=", value);
            return this;
        }

        public Criteria andNotEqualTo(String property, Object value) {
            addCriterion(column(property), "<>", value);
            return this;
        }

        public Criteria andGreaterThan(String property, Object value) {
            addCriterion(column(property), ">", value);
            return this;
        }

        public Criteria andGreaterThanOrEqualTo(String property, Object value) {
            addCriterion(column(property), ">=", value);
            return this;
        }

        public Criteria andLessThan(String property, Object value) {
            addCriterion(column(property), "<", value);
            return this;
        }

        public Criteria andLessThanOrEqualTo(String property, Object value) {
            addCriterion(column(property), "<=", value);
            return this;
        }

        public Criteria andIn(String property, Iterable values) {
            addCriterion(column(property), "in", values);
            return this;
        }

        public Criteria andNotIn(String property, Iterable values) {
            addCriterion(column(property), "not in", values);
            return this;
        }

        public Criteria andBetween(String property, Object value1, Object value2) {
            addCriterion(column(property), "between", value1, value2);
            return this;
        }

        public Criteria andNotBetween(String property, Object value1, Object value2) {
            addCriterion(column(property), "not between", value1, value2);
            return this;
        }

        public Criteria andLike(String property, String value, MatchMode matchMode) {
            addCriterion(column(property), "like", matchMode.toMatchString(value));
            return this;
        }

        public Criteria andNotLike(String property, String value, MatchMode matchMode) {
            addCriterion(column(property), "not like", matchMode.toMatchString(value));
            return this;
        }


        public Criteria orIsNull(String property) {
            addCriterion(new Criterion(column(property), "is null", true));
            return this;
        }

        public Criteria orIsNotNull(String property) {
            addOrCriterion(column(property), "is not null");
            return this;
        }

        public Criteria orEqualTo(String property, Object value) {
            addOrCriterion(column(property), "=", value);
            return this;
        }

        public Criteria orNotEqualTo(String property, Object value) {
            addOrCriterion(column(property), "<>", value);
            return this;
        }

        public Criteria orGreaterThan(String property, Object value) {
            addOrCriterion(column(property), ">", value);
            return this;
        }

        public Criteria orGreaterThanOrEqualTo(String property, Object value) {
            addOrCriterion(column(property), ">=", value);
            return this;
        }

        public Criteria orLessThan(String property, Object value) {
            addOrCriterion(column(property), "<", value);
            return this;
        }

        public Criteria orLessThanOrEqualTo(String property, Object value) {
            addOrCriterion(column(property), "<=", value);
            return this;
        }

        public Criteria orIn(String property, Iterable values) {
            addOrCriterion(column(property), "in", values);
            return this;
        }

        public Criteria orNotIn(String property, Iterable values) {
            addOrCriterion(column(property), "not in", values);
            return this;
        }

        public Criteria orBetween(String property, Object value1, Object value2) {
            addOrCriterion(column(property), "between", value1, value2);
            return this;
        }

        public Criteria orNotBetween(String property, Object value1, Object value2) {
            addOrCriterion(column(property), "not between", value1, value2);
            return this;
        }

        public Criteria orLike(String property, String value, MatchMode matchMode) {
            addOrCriterion(column(property), "like", matchMode.toMatchString(value));
            return this;
        }

        public Criteria orNotLike(String property, String value, MatchMode matchMode) {
            addOrCriterion(column(property), "not like", matchMode.toMatchString(value));
            return this;
        }

        public String getAndOr() {
            return andOr;
        }

        public Criteria setAndOr(String andOr) {
            this.andOr = andOr;
            return this;
        }


        public List<Criterion> getCriteria() {
            return criteria;
        }

        //属性和列对应
        protected Map<String, EntityColumn> propertyMap;

        protected Criteria(Map<String, EntityColumn> propertyMap) {
            super();
            this.propertyMap = propertyMap;
        }

        public String column(String property) {
            if (propertyMap != null && propertyMap.containsKey(property)) {
                return propertyMap.get(property).getColumn();
            } else {
                return property;
            }
        }

        public Criteria or() {
            return Condition.this.or();
        }

        public Criteria and() {
            return Condition.this.and();
        }

        public Condition end() {
            return Condition.this;
        }

    }
}