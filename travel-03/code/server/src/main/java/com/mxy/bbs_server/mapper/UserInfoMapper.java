package com.mxy.bbs_server.mapper;

import com.mxy.bbs_server.entity.User;
import com.mxy.bbs_server.entity.UserInfo;
import com.mxy.bbs_server.entity.UserInfoData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserInfoMapper {

    @Insert("insert into UserInfo (username, nickname, personalSign, avatarUrl, myPosts, myCollections) values (#{username}, #{nickname}, #{personalSign}, #{avatarUrl}, #{myPosts}, #{myCollections})")
    void add(UserInfoData userInfo);

    @Update("update UserInfo set nickname = #{nickname}, personalSign = #{personalSign}, avatarUrl = #{avatarUrl}, myPosts = #{myPosts}, myCollections = #{myCollections} where username = #{username}")
    void update(UserInfoData userInfo);

    @Select("select * from UserInfo where username = #{username}")
    UserInfoData query(UserInfoData userInfo);

    @Select("select * from UserInfo where username = #{username}")
    UserInfoData queryByUsername(String username);
}
