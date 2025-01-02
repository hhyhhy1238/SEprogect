package com.example.client.ui.component

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.programLogic.bean.Others
import com.example.client.ui.OtherPageActivity

class PersonAdapter(private val personList: List<Others>) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImageView: ImageView = itemView.findViewById(R.id.otherImageViewAvatar)
        val nicknameTextView: TextView = itemView.findViewById(R.id.otherTextViewNickname)

        init {
            itemView.setOnClickListener {
                val context = itemView.context
                val friend = personList[adapterPosition]
                // 执行跳转逻辑，启动 OtherPageActivity，并传递必要的数据
                val intent = Intent(context, OtherPageActivity::class.java)
                intent.putExtra("friendUsername", friend.ownerUserName)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = personList[position]
        holder.avatarImageView.setImageURI(friend.ownerAvatar)
        holder.nicknameTextView.text = friend.ownerNickName
    }

    override fun getItemCount(): Int {
        return personList.size
    }
}