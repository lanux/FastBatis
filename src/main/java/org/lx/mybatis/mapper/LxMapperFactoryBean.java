package org.lx.mybatis.mapper;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.helper.EntityHelper;
import org.lx.mybatis.provider.BaseSelectProvider;
import org.mybatis.spring.mapper.MapperFactoryBean;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

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
    public void initDao() throws Exception {
        Class<T> mapperInterface = super.getMapperInterface();
        if (hasFastMapper(mapperInterface)) {
            Class<?> entityClass = getEntityClassByInterface(mapperInterface);
            if (entityClass != null) {
                EntityHelper.resolveEntity(entityClass);
                Configuration configuration = this.getSqlSession().getConfiguration();
                Method[] methods = mapperInterface.getMethods();
                for (Method method : methods) {
                    addStatement(mapperInterface, entityClass, method.getName(), configuration);
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

    private synchronized void addStatement(Class<?> mapperClass, Class<?> entityClass, String methodName, Configuration configuration) {
        String statementId = mapperClass.getName() + "." + methodName;
        if (configuration.hasStatement(statementId)) {
            return;
        }
        if (methodName.equals("selectSelective")) {
//            LanguageDriver languageDriver = configuration.getDefaultScriptingLanguageInstance();
//            SqlSource script = languageDriver.createSqlSource(configuration, "<script>" + BaseSelectProvider.select(entityClass) + "</script>", entityClass);
//            MappedStatement.Builder builder = new MappedStatement.Builder(configuration, statementId, script, SqlCommandType.SELECT)
//                    .resultMaps(new ArrayList<>(configuration.getResultMaps()));
//            MappedStatement statement = builder.build();
//            configuration.addMappedStatement(statement);

//            if (SqlCommandType.INSERT.equals(statement.getSqlCommandType()) && statement.get != null && statement.getResultSetType().isAssignableFrom(entityClass)) {
//                keyGeneratorBuilder.build(statementBuilder, entityClass);
//            }
        }
    }


//
//    private MappedStatement buildNewStatement(SqlBuilder sqlBuilder, String statementId, Class<?> entityClass) {
//
//        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(this.configuration, statementId, sqlBuilder, sqlBuilder.getSqlCommandType());
//
//        if (sqlBuilder.getResultType() != null) {
//            List<ResultMap> resultMaps = new ArrayList<ResultMap>();
//            ResultMap.Builder resultMapBuilder = new ResultMap.Builder(configuration, statementBuilder.id() + "_Inline", sqlBuilder.getResultType(), sqlBuilder.getResultMappingList());
//            resultMaps.add(resultMapBuilder.build());
//            statementBuilder.resultMaps(resultMaps);
//        }
//
//        if (SqlCommandType.INSERT.equals(sqlBuilder.getSqlCommandType()) && sqlBuilder.getResultType() != null && sqlBuilder.getResultType().isAssignableFrom(entityClass)) {
//            keyGeneratorBuilder.build(statementBuilder, entityClass);
//        }
//        return statementBuilder.build();
//
//    }


}
