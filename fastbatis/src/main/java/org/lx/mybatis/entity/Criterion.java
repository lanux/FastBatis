package org.lx.mybatis.entity;

import java.util.Collection;

public class Criterion {
    private String column;

    private String condition;

    private Object value;

    private Object secondValue;

    private String andOr = "AND";

    private boolean noValue;

    private boolean singleValue;

    private boolean betweenValue;

    private boolean listValue;

    private String typeHandler;

    protected Criterion() {
    }

    protected Criterion(String column, String condition) {
        this(column, condition, false);
    }

    protected Criterion(String column, String condition, Object value, String typeHandler) {
        this(column, condition, value, typeHandler, false);
    }

    protected Criterion(String column, String condition, Object value) {
        this(column, condition, value, null, false);
    }

    protected Criterion(String column, String condition, Object value, Object secondValue, String typeHandler) {
        this(column, condition, value, secondValue, typeHandler, false);
    }

    protected Criterion(String column, String condition, Object value, Object secondValue) {
        this(column, condition, value, secondValue, null, false);
    }

    protected Criterion(String column, String condition, boolean isOr) {
        super();
        this.column = column;
        this.condition = condition;
        this.typeHandler = null;
        this.noValue = true;
        this.andOr = isOr ? "OR" : this.andOr;
    }

    protected Criterion(String column, String condition, Object value, String typeHandler, boolean isOr) {
        super();
        this.column = column;
        this.condition = condition;
        this.value = value;
        this.typeHandler = typeHandler;
        this.andOr = isOr ? "OR" : this.andOr;
        if (value instanceof Collection) {
            this.listValue = true;
        } else {
            this.singleValue = true;
        }
    }


    protected Criterion(String column, String condition, Object value, boolean isOr) {
        this(column, condition, value, null, isOr);
    }

    protected Criterion(String column, String condition, Object value, Object secondValue, String typeHandler, boolean isOr) {
        super();
        this.column = column;
        this.condition = condition;
        this.value = value;
        this.secondValue = secondValue;
        this.typeHandler = typeHandler;
        this.betweenValue = true;
        this.andOr = isOr ? "or" : "and";
    }

    protected Criterion(String column, String condition, Object value, Object secondValue, boolean isOr) {
        this(column, condition, value, secondValue, null, isOr);
    }

    public static Criterion instance() {
        return new Criterion();
    }

    public Criterion setColumn(String column) {
        this.column = column;
        return this;
    }

    public Criterion setCondition(String condition) {
        this.condition = condition;
        return this;
    }

    public Criterion setValue(Object value) {
        this.value = value;
        return this;
    }

    public Criterion setSecondValue(Object secondValue) {
        this.secondValue = secondValue;
        return this;
    }

    public Criterion setAndOr(String andOr) {
        this.andOr = andOr;
        return this;
    }

    public Criterion setNoValue(boolean noValue) {
        this.noValue = noValue;
        return this;
    }

    public Criterion setSingleValue(boolean singleValue) {
        this.singleValue = singleValue;
        return this;
    }

    public Criterion setBetweenValue(boolean betweenValue) {
        this.betweenValue = betweenValue;
        return this;
    }

    public Criterion setListValue(boolean listValue) {
        this.listValue = listValue;
        return this;
    }

    public Criterion setTypeHandler(String typeHandler) {
        this.typeHandler = typeHandler;
        return this;
    }

    public String getColumn() {
        return column;
    }

    public String getCondition() {
        return condition;
    }

    public Object getValue() {
        return value;
    }

    public Object getSecondValue() {
        return secondValue;
    }

    public String getAndOr() {
        return andOr;
    }

    public boolean isNoValue() {
        return noValue;
    }

    public boolean isSingleValue() {
        return singleValue;
    }

    public boolean isBetweenValue() {
        return betweenValue;
    }

    public boolean isListValue() {
        return listValue;
    }

    public String getTypeHandler() {
        return typeHandler;
    }

    public String columnCondition() {
        return this.column + " " + this.condition;
    }
}
