package com.example.client.ui.component

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.client.R
import com.example.client.ui.PostDetailActivity

class PostAdapter(private val postItems: List<PostItem>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postItem = postItems[position]
        holder.bind(postItem)
    }

    override fun getItemCount(): Int {
        return postItems.size
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewAvatar: ImageView = itemView.findViewById(R.id.imageViewAvatar)
        private val textViewNickname: TextView = itemView.findViewById(R.id.textViewNickname)
        private val textViewTimestamp: TextView = itemView.findViewById(R.id.textViewTimestamp)
        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val textViewContent: TextView = itemView.findViewById(R.id.textViewContent)

        init {
            itemView.setOnClickListener {
                val context = itemView.context
                val post = postItems[adapterPosition]
                // 执行跳转逻辑，启动 OtherPageActivity，并传递必要的数据
                val intent = Intent(context, PostDetailActivity::class.java)
                intent.putExtra("postOwnerUsername", post.owner.ownerUserName)
                intent.putExtra("postOwnerNickname", post.owner.ownerNickName)
                intent.putExtra("postOwnerAvatar", post.owner.ownerAvatar.toString())
                intent.putExtra("postId",post.id)
                context.startActivity(intent)
            }
        }

        fun bind(post: PostItem) {
            // 在此处根据post对象设置列表项的内容
            Glide.with(itemView.context)
                    .load(post.owner.ownerAvatar)
                    .into(imageViewAvatar)
            println(post.owner.ownerAvatar)
            textViewNickname.text = post.owner.ownerNickName
            textViewTimestamp.text = post.time
            textViewTitle.text = post.title
            textViewContent.text = post.content
        }
    }
}