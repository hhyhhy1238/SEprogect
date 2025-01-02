package com.example.client.programLogic.bean.review

data class ReviewResponse (
    val success: Boolean,
    val reviewResponseFailedReason: ReviewResponseFailedReason,
    val review:Review?
)