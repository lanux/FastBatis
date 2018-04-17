package org.lx.mybatis.entity;

import java.util.Collection;

public enum Operator {
    EQ("="), //= 运算符

    GE(">="), //>=运算符

    GT(">"), //> 运算符

    LE("<="), //<=运算符

    LT("<"), //< 运算符

    IS_NULL("IS NULL"), //IS NULL运算符

    IS_NOT_NULL("IS NOT NULL"), //IS NOT NULL运算符

    BETWEEN("BETWEEN"), //BETWEEN运算符

    LIKE("LIKE"), //LIKE运算符

    IN("IN"),//IN运算符

    NOT_BETWEEN("NOT BETWEEN"), //BETWEEN运算符

    NOT_LIKE("NOT LIKE"), //LIKE运算符

    NOT_IN("NOT IN"),//not IN运算符

    NE("<>"), //<>运算符

    AND("AND"), //AND运算符

    OR("OR"), //OR运算符

    NOT("NOT");

    String value;

    Operator(String value) {
        this.value = value;
    }

    public Criterion andCriterion(String column, Object... value) {
        if (value != null && value.length > 0) {
            if (value.length > 1) {

            } else if (value[0] instanceof Collection) {
                return  new Criterion(column,this.value,value[0]);
            }
        }
        return new Criterion(column, this.value, value);
    }

    public Criterion orCriterion(String column, Object... value) {
        return new Criterion(column, this.value, value);
    }

    public String getValue() {
        return value;
    }
}
