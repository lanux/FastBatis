package org.lx.mybatis.mapper;

import org.apache.ibatis.builder.annotation.MethodResolver;
import org.apache.ibatis.session.Configuration;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.builder.StatementProviderAnnotationBuilder;
import org.lx.mybatis.helper.EntityHolder;
import org.mybatis.spring.mapper.MapperFactoryBean;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

/**
 * use like @MapperScan(basePackages = "org.lx.mapper",factoryBean = FastMapperFactoryBean.class)
 */
public class FastMapperFactoryBean<T> extends MapperFactoryBean<T> {

    public FastMapperFactoryBean() {
    }

    public FastMapperFactoryBean(Class<T> mapperInterface) {
        super(mapperInterface);
    }

    @Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();
        Class<T> mapperInterface = super.getMapperInterface();
        if (hasFastMapper(mapperInterface)) {
            Class<?> entityClass = getEntityClassByInterface(mapperInterface);
            if (entityClass != null) {
                EntityHolder.resolveEntity(entityClass);
                Configuration configuration = this.getSqlSession().getConfiguration();
                Method[] methods = mapperInterface.getMethods();
                StatementProviderAnnotationBuilder annotationBuilder = new StatementProviderAnnotationBuilder(configuration, mapperInterface, entityClass);
                Collection<MethodResolver> incompleteMethods = configuration.getIncompleteMethods();
                synchronized (incompleteMethods) {
                    Iterator<MethodResolver> iter = incompleteMethods.iterator();
                    while (iter.hasNext()) {
                        iter.next().resolve();
                        iter.remove();
                    }
                }
                for (Method method : methods) {
                    String statementId = mapperInterface.getName() + "." + method.getName();
                    if (configuration.hasStatement(statementId) || method.isBridge()) {
                        continue;
                    }
                    annotationBuilder.parseStatement(method);
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
