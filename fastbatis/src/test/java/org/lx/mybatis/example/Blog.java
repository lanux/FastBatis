package org.lx.mybatis.example;

import org.apache.ibatis.type.JdbcType;
import org.lx.mybatis.annotation.Column;


public class Blog {
    private Long id;
    private String name;
    @Column(jdbcType = JdbcType.FLOAT)
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

}
