package org.lx.mybatis.example;

import org.lx.mybatis.mapper.base.InsertMapper;

public interface BlogMapper extends InsertMapper<Blog> {
    Blog selectBlog(int id);
}
