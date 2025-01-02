package com.mxy.bbs_server.mapper;

import com.mxy.bbs_server.entity.UserDetails;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDetailsMapper {
    @Select("select * from UserDetails where username=#{username}")
    UserDetails getUserDetailByUsername(String username);
    @Insert("insert into UserDetails(username, gender, birthday, registerTime,signature) values(#{username},#{gender},#{birthday},#{registerTime},#{signature}) ")
    void addUserDetails(UserDetails userDetails);
    @Update("update UserDetails set gender = #{gender},birthday=#{birthday},registerTime=#{registerTime},signature=#{signature} where username=#{username};")
    void updateUserDetailsByUsername(UserDetails userDetails);
}
