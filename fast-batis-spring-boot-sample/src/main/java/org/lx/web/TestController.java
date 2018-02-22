package org.lx.web;

import org.lx.mapper.UsersMapper;
import org.lx.model.Users;
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
    private UsersMapper usersMapper;

    @RequestMapping("/test")
    @ResponseBody
    public List<Users> test() {
        return usersMapper.selectSelective(new Users().setUsername("TEST1"));
    }
}
