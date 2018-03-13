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
        return usersMapper.selectSelective(new SysUser().setOrganizationId(1l));
    }

    @RequestMapping("/add")
    @ResponseBody
    public SysUser add() {
        SysUser test = new SysUser().setOrganizationId(22l).setUsername(RandomStringUtils.random(10));
        usersMapper.insertSelective(test);
//        usersMapper.selectByCondition(
//                new Condition(SysUser.class)
//                        .createCriteria()
//                        .andLike("name", "test", MatchMode.ANYWHERE)
//                        .andIn("id", Lists.newArrayList(1, 2, 3))
//                        .or()
//                        .andEqualTo("name", "")
//                        .end());

        return test;
    }
}
