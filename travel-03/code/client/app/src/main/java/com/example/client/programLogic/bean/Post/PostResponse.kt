package com.example.client.programLogic.bean.Post

data class PostResponse(
    val success: Boolean,
    val postResponseFailedReason:PostResponseFailedReason,
    val post: Post?
)