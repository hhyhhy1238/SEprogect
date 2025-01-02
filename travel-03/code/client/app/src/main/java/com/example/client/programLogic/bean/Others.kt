package com.example.client.programLogic.bean

import android.net.Uri

class Others {
    var ownerNickName: String
    var ownerUserName: String
    var ownerAvatar: Uri

    constructor(ownerNickName: String, ownerUserName: String, ownerAvatar: Uri) {
        this.ownerNickName = ownerNickName
        this.ownerUserName = ownerUserName
        this.ownerAvatar = ownerAvatar
    }

    constructor() {
        ownerNickName = "昵称"
        ownerUserName = "用户名"
        ownerAvatar = Uri.EMPTY
    }
}