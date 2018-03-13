package org.lx.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;
import org.lx.mybatis.annotation.Column;

@Table(name = "sys_user")
@Getter
@Setter
@Accessors(chain=true)
public class SysUser implements Serializable {
    @Id
    @Column(name = "id",jdbcType = JdbcType.BIGINT)
    private Long id;

    @Column(name = "organization_id",jdbcType = JdbcType.BIGINT)
    private Long organizationId;

    @Column(name = "username",jdbcType = JdbcType.VARCHAR)
    private String username;

    @Column(name = "password",jdbcType = JdbcType.VARCHAR)
    private String password;

    @Column(name = "salt",jdbcType = JdbcType.VARCHAR)
    private String salt;

    @Column(name = "locked",jdbcType = JdbcType.TINYINT)
    private Byte locked;

    private static final long serialVersionUID = 1L;
}