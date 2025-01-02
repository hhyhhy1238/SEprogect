package com.mxy.bbs_server.mapper;

import com.mxy.bbs_server.entity.Post;
import com.mxy.bbs_server.entity.PostData;
import com.mxy.bbs_server.entity.PostType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PostMapper  {

    @Insert("insert into Post (id, date, owner, title, content, images, likeNum, reviews) values (#{id}, #{date}, #{owner}, #{title}, #{content}, #{images}, #{likeNum}, #{reviews})")
    void add(PostData post);

    @Select("select * from Post where id = #{id}")
    PostData query(PostData post);

    @Select("select id from Post")
    List<String> selectAll();

    @Update("update Post set date = #{date}, owner = #{owner}, title = #{title}, content = #{content}, images = #{images}, likeNum = #{likeNum}, reviews = #{reviews} where id = #{id}")
    void update(PostData post);

    @Select("select * from Post where Post.PostType=#{PostType};")
    List<PostData> queryByPostType(String type);
}
