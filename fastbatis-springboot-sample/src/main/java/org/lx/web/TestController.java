package org.lx.web;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.lx.mapper.SysUserMapper;
import org.lx.model.SysUser;
import org.lx.mybatis.entity.Condition;
import org.lx.mybatis.entity.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lanux on 2018/2/22.
 */
@RestController
public class TestController {
    @Autowired
    private SysUserMapper usersMapper;

    @RequestMapping("/test")
    @ResponseBody
    public List<SysUser> test() {
        SysUser t = new SysUser().setOrganizationId(1l);
        int i = usersMapper.countBySelective(t);
        System.out.println("i = " + i);
        return usersMapper.selectBySelective(t);
    }

    @RequestMapping("/add")
    @ResponseBody
    public List<SysUser> add() {
        SysUser test = new SysUser().setOrganizationId(22l).setUsername(RandomStringUtils.random(10));
        usersMapper.insertSelective(test);
        List<SysUser> sysUsers = usersMapper.selectByCondition(
                new Condition(SysUser.class)
                        .createCriteria()
                        .andLike("username", "a", MatchMode.ANYWHERE)
                        .andIn("organizationId", Lists.newArrayList(1l, 22l))
                        .or()
                        .andEqualTo("username", "2")
                        .end());

        return sysUsers;
    }
}
