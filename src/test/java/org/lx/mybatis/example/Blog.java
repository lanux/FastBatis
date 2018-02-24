package org.lx.mybatis.example;

import org.lx.mybatis.entity.EntityColumn;
import org.lx.mybatis.entity.Selectable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Blog implements Selectable{
    private Long id;
    private String name;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public List<EntityColumn> select(Map<String, EntityColumn> propertyMap) {
        List<EntityColumn> list = new ArrayList<>();
        if (this.id!=null){
            list.add(propertyMap.get("id"));
        }
        return null;
    }
}
