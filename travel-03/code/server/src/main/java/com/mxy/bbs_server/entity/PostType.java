package com.mxy.bbs_server.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@AllArgsConstructor
@Getter
public enum PostType {
    POST_TYPE1("TYPE1"),
    POST_TYPE2("TYPE2"),
    POST_TYPE3("TYPE3");
    //只有POST_TYPE1,2,3可以加入，否则会返回无效的类型
    private final String value;
    public static boolean contains(String test) {
        for (PostType c : PostType.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}
