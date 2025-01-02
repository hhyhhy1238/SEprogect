package com.example.client.programLogic.bean

class UserInfo {
    val username: String
    val nickname: String?
    val avatarUrl: String?
    val gender: String?
    val birthday: String?
    val registerTime: String?
    val signature: String?
    val myPosts: List<String>
    val myCollections: List<String>

    constructor(
        username: String,
        nickname: String?,
        avatarUrl: String?,
        gender: String?,
        birthday: String?,
        registerTime: String?,
        signature: String?,
        myPosts: List<String>,
        myCollections: List<String>
    ) {
        this.username = username
        this.nickname = nickname
        this.avatarUrl = avatarUrl
        this.gender = gender
        this.birthday = birthday
        this.registerTime = registerTime
        this.signature = signature
        this.myPosts = myPosts
        this.myCollections = myCollections
    }
}