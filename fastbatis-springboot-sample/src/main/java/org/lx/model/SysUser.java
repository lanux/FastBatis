package org.lx.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;
import org.lx.mybatis.annotation.Column;
import org.lx.mybatis.annotation.Entity;

import java.io.Serializable;

@Entity(tableName = "sys_user")
@Getter
@Setter
@Accessors(chain = true)
public class SysUser implements Serializable {
    @Column(name = "id", jdbcType = JdbcType.BIGINT, id = true)
    private Long id;

    @Column(name = "organization_id", jdbcType = JdbcType.BIGINT)
    private Long organizationId;

    @Column(name = "username", jdbcType = JdbcType.VARCHAR)
    private String username;

    @Column(name = "password", jdbcType = JdbcType.VARCHAR)
    private String password;

    @Column(name = "salt", jdbcType = JdbcType.VARCHAR)
    private String salt;

    @Column(name = "locked", jdbcType = JdbcType.TINYINT)
    private Byte locked;

    private static final long serialVersionUID = 1L;
}