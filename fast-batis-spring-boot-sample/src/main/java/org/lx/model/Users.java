package org.lx.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.lx.mybatis.annotation.Column;

import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Accessors(chain=true)
@Table(name = "users")
public class Users implements Serializable {
    @Column(name = "status")
    private Integer status;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Id
    @Column(name = "id")
    private Integer id;

    private static final long serialVersionUID = 1L;
}