package com.mxy.bbs_server.mapper;

import com.mxy.bbs_server.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into User (username, password) values (#{username}, #{password})")
    void add(User user);

    @Select("select * from User where username = #{username}")
    User query(User user);


}
