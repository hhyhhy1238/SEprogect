package com.example.client.ui.component

import android.net.Uri


class CommentItem {
    var commenterUserName:String
    var commenterAdapter: Uri
    var commentTime:String
    var commenterNickname: String
    var commentContent: String

    constructor(
        commenterUserName: String,
        commenterAdapter: Uri,
        commenterNickname: String,
        commentTime: String,
        commentContent: String
    ) {
        this.commenterUserName = commenterUserName
        this.commenterAdapter = commenterAdapter
        this.commenterNickname = commenterNickname
        this.commentTime = commentTime
        this.commentContent = commentContent
    }
}