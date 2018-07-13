package org.lx.mybatis.entity;

import org.lx.mybatis.helper.EntityTables;
import org.lx.mybatis.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class Condition {

    protected List<Criteria> oredCriteria = new ArrayList<>();

    protected String alias;

    protected Class<?> entityClass;

    protected EntityTable table;

    protected Map<String, TableColumn> propertyMap;//属性和列对应

    protected boolean excludeBlob = false;

    protected List<OrderBy> orderBys = new ArrayList<>();

    public Condition(Class<?> entityClass) {
        this.entityClass = entityClass;
        table = EntityTables.getEntityTable(entityClass);
        propertyMap = table.getPropertyMap();
    }

    public String getAlias() {
        return alias;
    }

    public Condition alias(String alias) {
        this.alias = alias;
        return this;
    }

    protected Criteria or() {
        Criteria criteria = createCriteriaInternal();
        criteria.setAndOr(Operator.OR.value);
        oredCriteria.add(criteria);
        return criteria;
    }

    private Criteria and() {
        Criteria criteria = createCriteriaInternal();
        criteria.setAndOr(Operator.AND.value);
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            criteria.setAndOr(Operator.AND.value);
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

    public Condition orderBy(String property) {
        orderBy(property, false);
        return this;
    }

    public Condition orderBy(String property, Boolean descending) {
        this.orderBys.add(new OrderBy(property, descending));
        return this;
    }

    public boolean isExcludeBlob() {
        return excludeBlob;
    }

    public void setExcludeBlob(boolean excludeBlob) {
        this.excludeBlob = excludeBlob;
    }

    public void setOrderBys(List<OrderBy> orderBys) {
        this.orderBys = orderBys;
    }

    public List<OrderBy> getOrderBys() {
        return orderBys;
    }


    public class Criteria {
        protected List<Criterion> criteria = new ArrayList<>();

        protected String andOr = Operator.OR.value;//连接条件

        protected Criteria() {
            criteria = new ArrayList<>();
        }

        protected Criterion addCriterion(Criterion criterion) {
            criterion.setColumn(column(criterion.getColumn()));
            if (StringUtil.isNotEmpty(Condition.this.alias)) {
                criterion.setColumn(Condition.this.alias + "" + criterion.getColumn());
            }
            criteria.add(criterion);
            return criterion;
        }

        public Criteria andIsNull(String property) {
            addCriterion(Operator.IS_NULL.andCriterion(property));
            return this;
        }

        public Criteria andIsNotNull(String property) {
            addCriterion(Operator.IS_NOT_NULL.andCriterion(property));
            return this;
        }

        public Criteria andEqualTo(String property, Object value) {
            addCriterion(Operator.IS_NOT_NULL.andCriterion(property, value));
            return this;
        }

        public Criteria andNe(String property, Object value) {
            addCriterion(Operator.NE.andCriterion(property, value));
            return this;
        }

        public Criteria andGt(String property, Object value) {
            addCriterion(Operator.GT.andCriterion(property, value));
            return this;
        }

        public Criteria andGe(String property, Object value) {
            addCriterion(Operator.GE.andCriterion(property, value));
            return this;
        }

        public Criteria andLt(String property, Object value) {
            addCriterion(Operator.LT.andCriterion(property, value));
            return this;
        }

        public Criteria andLe(String property, Object value) {
            addCriterion(Operator.LE.andCriterion(property, value));
            return this;
        }

        public Criteria andIn(String property, Condition values) {
            addCriterion(Operator.IN.andCriterion(property, values));
            return this;
        }

        public Criteria andNotIn(String property, Collection values) {
            addCriterion(Operator.NOT_IN.andCriterion(property, values));
            return this;
        }

        public Criteria andBetween(String property, Object value1, Object value2) {
            addCriterion(Operator.BETWEEN.andCriterion(property, value1, value2));
            return this;
        }

        public Criteria andNotBetween(String property, Object value1, Object value2) {
            addCriterion(Operator.NOT_BETWEEN.andCriterion(property, value1, value2));
            return this;
        }

        public Criteria andLike(String property, String value, MatchMode matchMode) {
            addCriterion(Operator.LIKE.andCriterion(property, matchMode.toMatchString(value)));
            return this;
        }

        public Criteria andNotLike(String property, String value, MatchMode matchMode) {
            addCriterion(Operator.NOT_LIKE.andCriterion(property, matchMode.toMatchString(value)));
            return this;
        }


        public Criteria orIsNull(String property) {
            addCriterion(Operator.IS_NULL.orCriterion(property));
            return this;
        }

        public Criteria orIsNotNull(String property) {
            addCriterion(Operator.IS_NOT_NULL.orCriterion(property));
            return this;
        }

        public Criteria orEq(String property, Object value) {
            addCriterion(Operator.EQ.orCriterion(property, value));
            return this;
        }

        public Criteria orNe(String property, Object value) {
            addCriterion(Operator.NE.orCriterion(property, value));
            return this;
        }

        public Criteria orGt(String property, Object value) {
            addCriterion(Operator.GT.orCriterion(property, value));
            return this;
        }

        public Criteria orGe(String property, Object value) {
            addCriterion(Operator.GE.orCriterion(property, value));
            return this;
        }

        public Criteria orLt(String property, Object value) {
            addCriterion(Operator.LT.orCriterion(property, value));
            return this;
        }

        public Criteria orLe(String property, Object value) {
            addCriterion(Operator.LE.orCriterion(property, value));
            return this;
        }

        public Criteria orIn(String property, Collection values) {
            addCriterion(Operator.IN.orCriterion(property, values));
            return this;
        }

        public Criteria orNotIn(String property, Collection values) {
            addCriterion(Operator.NOT_IN.orCriterion(property, values));
            return this;
        }

        public Criteria orBetween(String property, Object value1, Object value2) {
            addCriterion(Operator.BETWEEN.orCriterion(property, value1, value2));
            return this;
        }

        public Criteria orNotBetween(String property, Object value1, Object value2) {
            addCriterion(Operator.NOT_BETWEEN.orCriterion(property, value1, value2));
            return this;
        }

        public Criteria orLike(String property, String value, MatchMode matchMode) {
            addCriterion(Operator.LIKE.orCriterion(property, matchMode.toMatchString(value)));
            return this;
        }

        public Criteria orNotLike(String property, String value, MatchMode matchMode) {
            addCriterion(Operator.NOT_LIKE.orCriterion(property, matchMode.toMatchString(value)));
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
        protected Map<String, TableColumn> propertyMap;

        protected Criteria(Map<String, TableColumn> propertyMap) {
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