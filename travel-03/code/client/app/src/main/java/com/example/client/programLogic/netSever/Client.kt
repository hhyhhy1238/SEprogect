package com.example.client.programLogic.netSever

import com.example.client.programLogic.bean.Location.LocationResponse
import com.example.client.programLogic.bean.Post.PostListResponse
import com.example.client.programLogic.bean.Post.PostResponse
import com.example.client.programLogic.bean.Post.PostResponseFailedReason
import com.example.client.programLogic.bean.Travel.TravelPlan
import com.example.client.programLogic.bean.Travel.TravelResponse
import com.example.client.programLogic.bean.User.User
import com.example.client.programLogic.bean.User.UserInfoResponse
import com.example.client.programLogic.bean.User.UserResponse
import com.example.client.programLogic.bean.UserInfo
import com.example.client.programLogic.bean.review.ReviewResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.concurrent.TimeUnit

object Client {
    private val client by lazy {
        OkHttpClient.Builder().connectTimeout(6000, TimeUnit.SECONDS)
            .readTimeout(6000, TimeUnit.SECONDS)
            .writeTimeout(6000, TimeUnit.SECONDS)
            .build()
    }

    private val gson by lazy {
        Gson()
    }
    fun login(username: String,password: String): UserResponse {
        val requestBody =
            gson.toJson(User(username, password)).toRequestBody(jsonMediaType.toMediaType())
        val request = Request.Builder().url("$serverUrl/userInfo/login").post(requestBody).build()
        val userResponse = client.newCall(request).execute()
        return gson.fromJson(userResponse.body?.string(), UserResponse::class.java)
    }


    fun addUser(user: User): UserResponse {
        val requestBody = gson.toJson(user).toRequestBody(jsonMediaType.toMediaType())
        val request = Request.Builder().url("$serverUrl/user/add").post(requestBody).build()
        val userResponse = client.newCall(request).execute()
        val UserResponse =
            gson.fromJson(userResponse.body?.string(), UserResponse::class.java)
        return UserResponse
    }

    fun getUserInfo1(username: String): UserInfo {
        val request = Request.Builder().url("$serverUrl/UserDetails/query/$username").get().build()
        val userResponse = client.newCall(request).execute()
        return gson.fromJson(userResponse.body?.string(), UserInfo::class.java)
    }

    fun getUserInfo2(username: String): UserResponse {
        val request = Request.Builder().url("$serverUrl/userInfo/query/$username").get().build()
        val userResponse = client.newCall(request).execute()
        return gson.fromJson(userResponse.body?.string(), UserResponse::class.java)
    }

    fun getUserFansOrCares(username: String): UserResponse {
        val request = Request.Builder().url("$serverUrl/FansAndConcerned/Toget/$username").get().build()
        val userResponse = client.newCall(request).execute()
        return gson.fromJson(userResponse.body?.string(), UserResponse::class.java)
    }

    fun updateUserInfo(
        username: String,
        nickname: String,
        birthday: String,
        gender: String,
        signature: String
    ):UserInfoResponse{
        val multipartBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("username", username)
            .addFormDataPart("nickname", nickname)
            .addFormDataPart("birthday", birthday)
            .addFormDataPart("gender", gender)
            .addFormDataPart("signature", signature)
            .build()
        val request =
            Request.Builder().url("$serverUrl/UserDetails/update").post(multipartBody).build()
        val userInfoResponse = client.newCall(request).execute()

        return gson.fromJson(userInfoResponse.body?.string(), UserInfoResponse::class.java)
    }

    fun getPost(postId: String?): PostResponse {
        if (postId == null) {
            return PostResponse(false, PostResponseFailedReason.POST_DOES_NOT_EXIST, null)
        }
        val request = Request.Builder().url("$serverUrl/post/query/$postId").get().build()
        val postResponse = client.newCall(request).execute()
        val remotePostResponse =
            gson.fromJson(postResponse.body?.string(), PostResponse::class.java)
        return remotePostResponse
    }

    fun getPostList(): PostListResponse {
        val request = Request.Builder().url("$serverUrl/postList/get").get().build()
        val postListResponse = client.newCall(request).execute()

        return gson.fromJson(postListResponse.body?.string(), PostListResponse::class.java)
    }

    fun getPostComment(reviewId: String): ReviewResponse {
        val request = Request.Builder().url("$serverUrl/review/query/$reviewId").get().build()
        val reviewResponse = client.newCall(request).execute()

        return gson.fromJson(reviewResponse.body?.string(), ReviewResponse::class.java)
    }

    fun makeTravelPlan(travelPlan: TravelPlan): TravelResponse{
        val requestBody = gson.toJson(travelPlan).toRequestBody(jsonMediaType.toMediaType())
        val request = Request.Builder().url("$serverUrl/travel/query").post(requestBody).build()
        val travelResponse = client.newCall(request).execute()
        return gson.fromJson(travelResponse.body?.string(), TravelResponse::class.java)
    }

    fun getLocationInfo(locationName: String): LocationResponse {
        val request = Request.Builder().url("$serverUrl/location/query/$locationName").get().build()
        val locationInfoResponse = client.newCall(request).execute()
        val Response =
            gson.fromJson(locationInfoResponse.body?.string(), LocationResponse::class.java)
        return Response
    }
//
//    fun addReview(
//        id: String,
//        targetPost: String,
//        username: String,
//        content: String,
//        images: List<File>
//    ): ReviewResponse {
//        val multipartBodyBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
//            .addFormDataPart("id", id)
//            .addFormDataPart("targetPost", targetPost)
//            .addFormDataPart("username", username)
//            .addFormDataPart("content", content)
//        if (images.isEmpty()) {
//            multipartBodyBuilder.addFormDataPart("images", "", "".toRequestBody(null))
//        }
//        for (image in images) {
//            val currentImageRequestBody = image.asRequestBody(MultipartBody.FORM)
//            multipartBodyBuilder.addFormDataPart("images", image.name, currentImageRequestBody)
//        }
//        val multipartBody = multipartBodyBuilder.build()
//        val request = Request.Builder().url("$serverUrl/review/add").post(multipartBody).build()
//        val reviewResponse = client.newCall(request).execute()
//        val remoteReviewResponse =
//            gson.fromJson(reviewResponse.body?.string(), ReviewResponse::class.java)
////        if (remoteReviewResponse.success!!) {
////            try {
////                cacheRepository.deletePost(Post(targetPost, "", "", "", "", listOf(), 0, listOf()))
////            } catch (e: Exception) {
////                Log.d("Client", "被捕获的异常: $e")
////            }
////        }
//        return remoteReviewResponse
//    }
//
    fun addPost(
        id: String,
        planId: String,
        owner: String,
        title: String,
        content: String
    ): PostResponse {
        val multipartBodyBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("id", id)
            .addFormDataPart("planId",planId)
            .addFormDataPart("owner", owner)
            .addFormDataPart("title", title)
            .addFormDataPart("content", content)
        val multipartBody = multipartBodyBuilder.build()
        val request = Request.Builder().url("$serverUrl/post/addWithoutImage").post(multipartBody).build()
        val postResponse = client.newCall(request).execute()
        val remotePostResponse =
            gson.fromJson(postResponse.body?.string(), PostResponse::class.java)
        return remotePostResponse
    }
}