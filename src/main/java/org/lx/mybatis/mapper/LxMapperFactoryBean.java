package org.lx.mybatis.mapper;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.mapper.MapperFactoryBean;

/**
 *
 * @MapperScan(basePackages = "",factoryBean = LxMapperFactoryBean.class)
 */
public class LxMapperFactoryBean<T> extends MapperFactoryBean<T> {

    @Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();
        Configuration configuration = getSqlSession().getConfiguration();
        // TODO
    }
}
