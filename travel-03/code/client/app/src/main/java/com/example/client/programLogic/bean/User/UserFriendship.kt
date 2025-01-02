package com.example.client.programLogic.bean.User

data class UserFriendship (
    val username :String,
    val myConcerned:List<String>,
    val myFans:List<String>,
    val numConcerned:Integer,
    val numFans:Integer
)