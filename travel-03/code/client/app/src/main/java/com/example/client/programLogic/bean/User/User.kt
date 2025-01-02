package com.example.client.programLogic.bean.User

import android.net.Uri
import java.io.Serializable

data class User(var username: String, var password: String) : Serializable {

    override fun toString(): String {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}'
    }
}