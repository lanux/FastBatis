package org.lx.mybatis.entity;

public class OrderBy {
    private String column;
    private boolean descending;
    private boolean nullsFirst;
    private boolean nullsLast;

    public OrderBy() {
    }

    public OrderBy(String column) {
        this.column = column;
    }

    public OrderBy(String column, boolean descending) {
        this.column = column;
        this.descending = descending;
    }

    public OrderBy setNullsFirst(boolean nullsFirst) {
        this.nullsFirst = nullsFirst;
        return this;
    }

    public OrderBy setNullsLast(boolean nullsLast) {
        this.nullsLast = nullsLast;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder var1 = new StringBuilder(column);
        if (this.descending) {
            var1.append(" DESC");
        }
        if (this.nullsFirst) {
            var1.append(" NULLS FIRST");
        } else if (this.nullsLast) {
            var1.append(" NULLS LAST");
        }
        return var1.toString();
    }
}
