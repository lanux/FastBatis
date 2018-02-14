package org.lx.mybatis.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface Selectable {

    default List<EntityColumn> select(Collection<EntityColumn> columns) {
        Map<String, EntityColumn> map = columns.stream().collect(Collectors.toMap(EntityColumn::getProperty, p -> p));
        return select(map);
    }

    List<EntityColumn> select(Map<String, EntityColumn> propertyMap);

}
