package org.lx.mybatis.entity;

import java.util.Map;

public class Criteria extends GeneratedCriteria {

    //属性和列对应
    protected Map<String, EntityColumn> propertyMap;

    protected Criteria(Map<String, EntityColumn> propertyMap) {
        super();
        this.propertyMap = propertyMap;
    }

    public String column(String property) {
        if (propertyMap.containsKey(property)) {
            return propertyMap.get(property).getColumn();
        } else {
            return null;
        }
    }

}
