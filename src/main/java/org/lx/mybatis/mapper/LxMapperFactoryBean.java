package org.lx.mybatis.mapper;

import org.mybatis.spring.mapper.MapperFactoryBean;

/**
 * @MapperScan(basePackages = "",factoryBean = LxMapperFactoryBean.class)
 */
public class LxMapperFactoryBean<T> extends MapperFactoryBean<T> {
    public void initDao() throws Exception {
    }
}
