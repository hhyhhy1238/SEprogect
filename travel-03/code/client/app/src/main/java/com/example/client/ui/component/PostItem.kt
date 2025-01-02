package com.example.client.ui.component

import android.net.Uri
import com.example.client.programLogic.bean.Others

class PostItem(
    var title: String,
    var owner: Others,
    var image: Uri,
    var content: String,
    var time: String,
    var id: String
){
    override fun toString(): String {
        return "PostItem(title='$title', owner=$owner, image=$image, content='$content', time='$time')"
    }
}