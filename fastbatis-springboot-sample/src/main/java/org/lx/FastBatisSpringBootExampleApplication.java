package org.lx;

import org.lx.mybatis.mapper.FastMapperFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "org.lx.mapper", factoryBean = FastMapperFactoryBean.class)
public class FastBatisSpringBootExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastBatisSpringBootExampleApplication.class, args);
    }
}
