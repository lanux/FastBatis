package org.lx.mybatis.entity;

public enum ValueMode {
    HASH("#"), DOLLAR("$");
    String value;

    ValueMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
