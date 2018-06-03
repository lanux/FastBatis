package org.lx.mybatis.entity;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.lx.mybatis.util.StringUtil;

/**
 * 数据库表对应的列
 */
public class TableColumn {
    private String property;
    private String column;
    private Class<?> javaType;
    private JdbcType jdbcType;
    private Class<? extends TypeHandler<?>> typeHandler;
    private boolean id = false;
    private boolean blob = false;
    private boolean nullAble = true;
    private boolean insertAble = true;
    private boolean updateAble = true;
    private String defaultValue;// insert or update时候，sql常量值，比如 序列ID

    public TableColumn() {
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Class<? extends TypeHandler<?>> getTypeHandler() {
        return typeHandler;
    }

    public void setTypeHandler(Class<? extends TypeHandler<?>> typeHandler) {
        this.typeHandler = typeHandler;
    }

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public boolean isBlob() {
        return blob;
    }

    public void setBlob(boolean blob) {
        this.blob = blob;
    }

    public boolean isNullAble() {
        return nullAble;
    }

    public void setNullAble(boolean nullAble) {
        this.nullAble = nullAble;
    }

    public boolean isInsertAble() {
        return insertAble;
    }

    public void setInsertAble(boolean insertAble) {
        this.insertAble = insertAble;
    }

    public boolean isUpdateAble() {
        return updateAble;
    }

    public void setUpdateAble(boolean updateAble) {
        this.updateAble = updateAble;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
