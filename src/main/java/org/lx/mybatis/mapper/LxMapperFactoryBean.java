package org.lx.mybatis.mapper;

import org.apache.ibatis.jdbc.SqlBuilder;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;
import org.lx.mybatis.annotation.FastMapper;
import org.lx.mybatis.helper.EntityHelper;
import org.mybatis.spring.mapper.MapperFactoryBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @MapperScan(basePackages = "",factoryBean = LxMapperFactoryBean.class)
 */
public class LxMapperFactoryBean<T> extends MapperFactoryBean<T> {

    @Override
    public void initDao() throws Exception {
        if (hasFastMapper(super.getMapperInterface())) {
            Class<?> entityClass = getEntityClassByInterface(super.getMapperInterface());
            if (entityClass!=null){
                EntityHelper.resolveEntity(entityClass);
                Configuration configuration = this.getSqlSession().getConfiguration();
                Type[] types = super.getMapperInterface().getGenericInterfaces();
                LanguageDriver languageDriver = configuration.getDefaultScriptingLanguageInstance();
                SqlSource script = languageDriver.createSqlSource(configuration, "sql", null);
                MappedStatement statement = new MappedStatement.Builder(configuration, "", script, SqlCommandType.SELECT).build();
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
                    //参数化类型
                    ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
                    //返回表示此类型实际类型参数的 Type 对象的数组
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    return (Class<T>) actualTypeArguments[0];
                }
            }
        }
        return null;
    }

//    private synchronized String addStatement(Class<?> mapperClass, String methodName, Configuration configuration) {
//        LanguageDriver languageDriver = configuration.getDefaultScriptingLanguageInstance();
//        SqlSource script = languageDriver.createSqlSource(configuration, "script", null);
//        // 定义子mapper的statement1
//        String baseStatementId = mapperClass.getName() + "." + methodName;
//        if (configuration.hasStatement(baseStatementId)) {
//            return baseStatementId;
//        }
//        if (configuration.hasStatement(baseStatementId) || vpmConfiguration.hasStatement(baseStatementId)) {
//            // 复制声明
//            MappedStatement baseStatement = vpmConfiguration.getMappedStatement(baseStatementId);
//
//            if (!configuration.hasStatement(baseStatementId)) {
//                // 创建新的statement,并封装相应的resultType
//                MappedStatement mappedSt = buildNewStatement(mapperClass, baseStatement, baseStatementId);
//                // 添加进configuration即可
//                configuration.addMappedStatement(mappedSt);
//            }
//        } else {
//            // 通过sql builder创建
//            Class<?> entityClass = this.getEntityClassByInterface(mapperClass);
//            SqlBuilder sqlBuilder;
//            try {
//                Class<?> sqlSourceClass = HBatisConfiguration.getSqlSourceClass(this.dialect, methodName);
//                Constructor<?> constructor = sqlSourceClass.getDeclaredConstructor(SqlSourceBuilder.class, Class.class);
//                sqlBuilder = (SqlBuilder) constructor.newInstance(this.sqlSourceBuilder, entityClass);
//
//            } catch (Exception e) {
//                throw new RuntimeException("build statement error[id:" + statementId + "]", e);
//            }
//
//            MappedStatement mappedSt = this.buildNewStatement(sqlBuilder, statementId, entityClass);
//
//            configuration.addMappedStatement(mappedSt);
//        }
//        return statementId;
//    }
//
//    private MappedStatement buildNewStatement(Class<?> mapperClass, MappedStatement baseStatement, String statementId) {
//        Class<?> entityClass = this.getEntityClassByInterface(mapperClass);
//
//        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(baseStatement.getConfiguration(), statementId, baseStatement.getSqlSource(), baseStatement.getSqlCommandType());
//        statementBuilder.resultMaps(baseStatement.getResultMaps());
//        if (statementId.endsWith("selectByStatement")) {// 特殊处理
//
//            List<ResultMapping> resultMappings = ResultMapsBuilder.buildResultMappings(configuration, TableMappingUtil.getEntityMapping(entityClass));
//
//            ResultMap.Builder builder = new ResultMap.Builder(configuration, statementBuilder.id() + "_ResultMap", entityClass, resultMappings);
//            statementBuilder.resultMaps(Arrays.asList(builder.build()));
//        }
//        return statementBuilder.build();
//
//    }
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
