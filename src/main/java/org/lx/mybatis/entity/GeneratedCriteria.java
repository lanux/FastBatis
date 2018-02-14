package org.lx.mybatis.entity;

import java.util.ArrayList;
import java.util.List;

public class GeneratedCriteria {
    protected List<Criterion> criteria;

    protected String andOr = "OR";//连接条件

    protected GeneratedCriteria() {
        criteria = new ArrayList<>();
    }

    protected String column(String property) {
        return property;
    }

    protected void addCriterion(Criterion criterion) {
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

    public GeneratedCriteria andIsNull(String property) {
        addCriterion(column(property), "is null");
        return this;
    }

    public GeneratedCriteria andIsNotNull(String property) {
        addCriterion(column(property), "is not null");
        return this;
    }

    public GeneratedCriteria andEqualTo(String property, Object value) {
        addCriterion(column(property), "=", value);
        return this;
    }

    public GeneratedCriteria andNotEqualTo(String property, Object value) {
        addCriterion(column(property), "<>", value);
        return this;
    }

    public GeneratedCriteria andGreaterThan(String property, Object value) {
        addCriterion(column(property), ">", value);
        return this;
    }

    public GeneratedCriteria andGreaterThanOrEqualTo(String property, Object value) {
        addCriterion(column(property), ">=", value);
        return this;
    }

    public GeneratedCriteria andLessThan(String property, Object value) {
        addCriterion(column(property), "<", value);
        return this;
    }

    public GeneratedCriteria andLessThanOrEqualTo(String property, Object value) {
        addCriterion(column(property), "<=", value);
        return this;
    }

    public GeneratedCriteria andIn(String property, Iterable values) {
        addCriterion(column(property), "in", values);
        return this;
    }

    public GeneratedCriteria andNotIn(String property, Iterable values) {
        addCriterion(column(property), "not in", values);
        return this;
    }

    public GeneratedCriteria andBetween(String property, Object value1, Object value2) {
        addCriterion(column(property), "between", value1, value2);
        return this;
    }

    public GeneratedCriteria andNotBetween(String property, Object value1, Object value2) {
        addCriterion(column(property), "not between", value1, value2);
        return this;
    }

    public GeneratedCriteria andLike(String property, String value) {
        addCriterion(column(property), "like", value);
        return this;
    }

    public GeneratedCriteria andNotLike(String property, String value) {
        addCriterion(column(property), "not like", value);
        return this;
    }


    public GeneratedCriteria orIsNull(String property) {
        addCriterion(new Criterion(column(property), "is null", true));
        return this;
    }

    public GeneratedCriteria orIsNotNull(String property) {
        addOrCriterion(column(property), "is not null");
        return this;
    }

    public GeneratedCriteria orEqualTo(String property, Object value) {
        addOrCriterion(column(property), "=", value);
        return this;
    }

    public GeneratedCriteria orNotEqualTo(String property, Object value) {
        addOrCriterion(column(property), "<>", value);
        return this;
    }

    public GeneratedCriteria orGreaterThan(String property, Object value) {
        addOrCriterion(column(property), ">", value);
        return this;
    }

    public GeneratedCriteria orGreaterThanOrEqualTo(String property, Object value) {
        addOrCriterion(column(property), ">=", value);
        return this;
    }

    public GeneratedCriteria orLessThan(String property, Object value) {
        addOrCriterion(column(property), "<", value);
        return this;
    }

    public GeneratedCriteria orLessThanOrEqualTo(String property, Object value) {
        addOrCriterion(column(property), "<=", value);
        return this;
    }

    public GeneratedCriteria orIn(String property, Iterable values) {
        addOrCriterion(column(property), "in", values);
        return this;
    }

    public GeneratedCriteria orNotIn(String property, Iterable values) {
        addOrCriterion(column(property), "not in", values);
        return this;
    }

    public GeneratedCriteria orBetween(String property, Object value1, Object value2) {
        addOrCriterion(column(property), "between", value1, value2);
        return this;
    }

    public GeneratedCriteria orNotBetween(String property, Object value1, Object value2) {
        addOrCriterion(column(property), "not between", value1, value2);
        return this;
    }

    public GeneratedCriteria orLike(String property, String value) {
        addOrCriterion(column(property), "like", value);
        return this;
    }

    public GeneratedCriteria orNotLike(String property, String value) {
        addOrCriterion(column(property), "not like", value);
        return this;
    }

    public String getAndOr() {
        return andOr;
    }

    public GeneratedCriteria setAndOr(String andOr) {
        this.andOr = andOr;
        return this;
    }

    public List<Criterion> getCriteria() {
        return criteria;
    }

    public boolean isNotEmpty() {
        return criteria != null && criteria.size() > 0;
    }

}
