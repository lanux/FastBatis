package org.lx.mybatis.mapper;

import org.apache.ibatis.session.Configuration;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.builder.StatementProviderAnnotationBuilder;
import org.lx.mybatis.helper.EntityHelper;
import org.mybatis.spring.mapper.MapperFactoryBean;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * use like @MapperScan(basePackages = "org.lx.mapper",factoryBean = LxMapperFactoryBean.class)
 */
public class LxMapperFactoryBean<T> extends MapperFactoryBean<T> {

    public LxMapperFactoryBean() {
    }

    public LxMapperFactoryBean(Class<T> mapperInterface) {
        super(mapperInterface);
    }

    @Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();
        Class<T> mapperInterface = super.getMapperInterface();
        if (hasFastMapper(mapperInterface)) {
            Class<?> entityClass = getEntityClassByInterface(mapperInterface);
            if (entityClass != null) {
                EntityHelper.resolveEntity(entityClass);
                Configuration configuration = this.getSqlSession().getConfiguration();
                Method[] methods = mapperInterface.getMethods();
                StatementProviderAnnotationBuilder annotationBuilder = new StatementProviderAnnotationBuilder(configuration, mapperInterface);
                for (Method method : methods) {
                    String statementId = mapperInterface.getName() + "." + method.getName();
                    if (configuration.hasStatement(statementId) || method.isBridge()) {
                        continue;
                    }
                    annotationBuilder.parseStatement(method,entityClass);
                }
            }
        }
    }


    private boolean hasFastMapper(Class<?> mapperInterface) {
        Class<?>[] interfaces = mapperInterface.getInterfaces();
        boolean hasFastMapper = false;
        if (interfaces != null && interfaces.length > 0) {
            for (Class<?> anInterface : interfaces) {
                if (anInterface.isAnnotationPresent(FastMapper.class)) {
                    hasFastMapper = true;
                    break;
                }
            }
        }
        return hasFastMapper;
    }

    private Class<?> getEntityClassByInterface(Class<T> mapperInterface) {
        Type[] types = mapperInterface.getGenericInterfaces();
        if (types != null) {
            for (Type genericSuperclass : types) {
                if (genericSuperclass instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    return (Class<T>) actualTypeArguments[0];
                }
            }
        }
        return null;
    }


}
