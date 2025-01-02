package com.example.client.ui.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.client.R
import com.example.client.ui.appContext

class CommentAdapter (private val commentList: List<CommentItem>):RecyclerView.Adapter<CommentAdapter.CommentViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val CommentItem = commentList[position]
        holder.binding(CommentItem)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentImageViewAvatar: ImageView = itemView.findViewById(R.id.commentImageViewAvatar)
        val commentTextViewNickname: TextView = itemView.findViewById(R.id.commentTextViewNickname)
        val commentTime: TextView = itemView.findViewById(R.id.commentTextViewTime)
        val commentContent: TextView = itemView.findViewById(R.id.commentTextViewContent)

        fun binding(comment : CommentItem){
            commentTextViewNickname.text = comment.commenterNickname
            commentTime.text = comment.commentTime
            commentContent.text = comment.commentContent
            Glide.with(itemView)
                .load(comment.commenterAdapter)
                .into(commentImageViewAvatar)
        }
    }
}