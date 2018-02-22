package org.lx.mapper;

import org.lx.model.Users;
import org.lx.mybatis.mapper.base.CrudMapper;
import org.lx.mybatis.mapper.selective.CrudSelectiveMapper;

public interface UsersMapper extends CrudSelectiveMapper<Users>, CrudMapper<Users> {
}