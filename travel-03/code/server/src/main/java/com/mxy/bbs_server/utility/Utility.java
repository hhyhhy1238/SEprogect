package com.mxy.bbs_server.utility;

import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.io.Resources;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;
@Log
public class Utility {

    public static final Gson gson = new Gson();
    public static String saveAvatar(MultipartFile avatar, String username) throws IOException {
        Properties properties=new Properties();
        properties.load(Resources.getResourceAsStream("resourceLocations.properties"));
        String Avatar_location=properties.getProperty("Avatar_location");
        //final var targetAvatar = new File("./avatars/" + username + "/" + avatar.getOriginalFilename());
        final var targetAvatar = new File(Avatar_location + username + "/" + avatar.getOriginalFilename());

        String tempUrl=(Avatar_location + username + "/" + avatar.getOriginalFilename()).substring(1);


        FileUtils.copyInputStreamToFile(avatar.getInputStream(), targetAvatar);
        return NginxHelper.getAbsoluteUrl(tempUrl);
    }

    public static List<String> savePostImages(MultipartFile[] images, String postId) throws IOException {
        Properties properties=new Properties();
        properties.load(Resources.getResourceAsStream("resourceLocations.properties"));
        String PostImages_location=properties.getProperty("PostImages_location");

        final List<String> imagesLst = new ArrayList<>();
        for (final var image: images) {
            if (Objects.requireNonNull(image.getOriginalFilename()).isEmpty()) {
                continue;
            }
            //final var targetImage = new File("./post_images/" + postId + "/" + image.getOriginalFilename());
            final var targetImage = new File(PostImages_location + postId + "/" + image.getOriginalFilename());

            String tempUrl=(PostImages_location + postId + "/" + image.getOriginalFilename()).substring(1);
            //log.info(tempUrl);

            FileUtils.copyInputStreamToFile(image.getInputStream(), targetImage);
            //log.info(NginxHelper.getAbsoluteUrl(tempUrl));
            imagesLst.add(NginxHelper.getAbsoluteUrl(tempUrl));
        }
        return imagesLst;
    }

    public static List<String> saveAvatarImages(MultipartFile[] images, String postId) throws IOException {
        Properties properties=new Properties();
        properties.load(Resources.getResourceAsStream("resourceLocations.properties"));
        String AvatarImages_location=properties.getProperty("AvatarImages_location");
        final List<String> imagesLst = new ArrayList<>();
        for (final var image: images) {
            if (Objects.requireNonNull(image.getOriginalFilename()).isEmpty()) {
                continue;
            }
            //final var targetImage = new File("./avatars_images/" + postId + "/" + image.getOriginalFilename());
            final var targetImage = new File(AvatarImages_location + postId + "/" + image.getOriginalFilename());

            String tempUrl=(AvatarImages_location + postId + "/" + image.getOriginalFilename()).substring(1);


            FileUtils.copyInputStreamToFile(image.getInputStream(), targetImage);
            imagesLst.add(NginxHelper.getAbsoluteUrl(tempUrl));
        }
        return imagesLst;
    }

    public static List<String> saveReviewImages(MultipartFile[] images, String reviewId) throws IOException {
        Properties properties=new Properties();
        properties.load(Resources.getResourceAsStream("resourceLocations.properties"));
        String ReviewImages_location=properties.getProperty("ReviewImages_location");
        final List<String> imagesLst = new ArrayList<>();
        for (final var image: images) {
            if (Objects.requireNonNull(image.getOriginalFilename()).isEmpty()) {
                continue;
            }
            //final var targetImage = new File("./review_images/" + reviewId + "/" + image.getOriginalFilename());
            final var targetImage = new File(ReviewImages_location + reviewId + "/" + image.getOriginalFilename());

            String tempUrl=(ReviewImages_location + reviewId + "/" + image.getOriginalFilename()).substring(1);

            FileUtils.copyInputStreamToFile(image.getInputStream(), targetImage);
            imagesLst.add(NginxHelper.getAbsoluteUrl(tempUrl));
        }
        return imagesLst;
    }

    public static String getDate(String format) {
        return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }
}
