package org.lx.mybatis.entity;

import java.util.ArrayList;
import java.util.List;

public class SelectCondition extends Condition {

    protected boolean selectBlob = true;

    protected List<OrderBy> orderBys = new ArrayList<>();

    public SelectCondition(Class<?> entityClass) {
        super(entityClass);
    }


    public Condition orderBy(String property) {
        orderBy(property, false);
        return this;
    }

    public Condition orderBy(String property, Boolean descending) {
        this.orderBys.add(new OrderBy(property, descending));
        return this;
    }

    public boolean isSelectBlob() {
        return selectBlob;
    }

    public Condition selectBlob(boolean selectBlob) {
        this.selectBlob = selectBlob;
        return this;
    }

    public List<OrderBy> getOrderBys() {
        return orderBys;
    }
}
