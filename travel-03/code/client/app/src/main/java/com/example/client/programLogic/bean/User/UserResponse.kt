package com.example.client.programLogic.bean.User

import com.example.client.programLogic.bean.UserInfo

data class UserResponse(
    val success: Boolean?,
    val userResponseFailedReason: UserResponseFailedReason?,
    val userInfo: UserInfo?,
    val friendship: UserFriendship?
)