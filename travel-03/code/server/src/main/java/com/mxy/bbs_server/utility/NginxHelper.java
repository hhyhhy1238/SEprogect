package com.mxy.bbs_server.utility;

import lombok.extern.java.Log;
import org.apache.ibatis.io.Resources;

import java.util.Properties;
@Log
public class NginxHelper {

    public static String getAbsoluteUrl(String Path)  {
        Properties properties=new Properties();
        try{
            properties.load(Resources.getResourceAsStream("NGINX_PORT.properties"));
        }catch (Exception e){
            log.info(e.getMessage());
        }
        String NGINX_PORT=properties.getProperty("NGINX_PORT");
        //TODO: 应该对absolutePath处理一下
        //log.info(NGINX_PORT+Path);
        return NGINX_PORT + Path;
    }
}
