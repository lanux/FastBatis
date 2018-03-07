package org.lx.mapper;

import org.lx.model.SysUser;
import org.lx.mybatis.mapper.base.CrudMapper;
import org.lx.mybatis.mapper.condition.ConditionMapper;
import org.lx.mybatis.mapper.selective.CrudSelectiveMapper;

/**
 * 
 * 
 * sys_user
 */
public interface SysUserMapper extends CrudSelectiveMapper<SysUser>, CrudMapper<SysUser> ,ConditionMapper<SysUser>{
}