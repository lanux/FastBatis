package org.lx.mybatis.annotation;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    String name() default "";

    boolean id() default false;// 是否唯一

    boolean nullAble() default true;//是否允许为空

    boolean isBlob() default false;// 是否blob字段

    String delimiter() default "";// 分隔符

    boolean insertAble() default true;

    boolean updateAble() default true;

    boolean reserved() default false;//保留字

    JdbcType jdbcType() default JdbcType.UNDEFINED;

//    Class<? extends TypeHandler<?>> typeHandler() default UnknownTypeHandler.class;

    String defaultValue() default "";//insert or update时候如果有自定义值则使用自定义值，否则使用默认值，
}