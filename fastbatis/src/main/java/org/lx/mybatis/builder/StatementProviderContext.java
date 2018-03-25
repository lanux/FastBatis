package org.lx.mybatis.builder;

import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Method;

public class StatementProviderContext {
    private Configuration configuration;
    private Class<?> mapperType;
    private Method mapperMethod;
    private Class<?> entityClass;

    public Configuration getConfiguration() {
        return configuration;
    }

    public StatementProviderContext setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public Class<?> getMapperType() {
        return mapperType;
    }

    public StatementProviderContext setMapperType(Class<?> mapperType) {
        this.mapperType = mapperType;
        return this;
    }

    public Method getMapperMethod() {
        return mapperMethod;
    }

    public StatementProviderContext setMapperMethod(Method mapperMethod) {
        this.mapperMethod = mapperMethod;
        return this;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public StatementProviderContext setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
        return this;
    }
}
