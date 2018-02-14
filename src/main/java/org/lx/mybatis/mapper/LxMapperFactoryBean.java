package org.lx.mybatis.mapper;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.mapper.MapperFactoryBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @MapperScan(basePackages = "",factoryBean = LxMapperFactoryBean.class)
 */
public class LxMapperFactoryBean<T> extends MapperFactoryBean<T> {

    @Override
    public void initDao() throws Exception {
        Configuration configuration = this.getSqlSession().getConfiguration();
        Class<T> mapperInterface = super.getMapperInterface();
        Type[] types = getClass().getGenericInterfaces();
        for (Type genericSuperclass : types) {
            if(genericSuperclass instanceof ParameterizedType){
                //参数化类型
                ParameterizedType parameterizedType= (ParameterizedType) genericSuperclass;
                //返回表示此类型实际类型参数的 Type 对象的数组
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                Class entityClass= (Class<T>)actualTypeArguments[0];
            }else{
                Class entityClass= (Class<T>)genericSuperclass;
            }
        }

    }
}
