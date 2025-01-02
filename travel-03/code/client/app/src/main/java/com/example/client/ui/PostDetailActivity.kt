package com.example.client.ui

import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.client.R
import com.example.client.databinding.ActivityPostDetailBinding
import com.example.client.programLogic.bean.Others
import com.example.client.programLogic.netSever.Client
import com.example.client.programLogic.netSever.serverUrl
import com.example.client.ui.component.CommentAdapter
import com.example.client.ui.component.CommentItem
import com.example.client.ui.component.LocationAdapter
import com.example.client.ui.component.LocationItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Locale


class PostDetailActivity : AppCompatActivity() {

    private lateinit var id:String
    private lateinit var userName :String
    private lateinit var nickName: TextView
    private lateinit var avatar: ImageView
    private lateinit var avatarUri: Uri
    private lateinit var careButton: Button
    private lateinit var title: TextView
    private lateinit var content: TextView
    private lateinit var locationsUI : RecyclerView
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var likeButton: ImageButton
    private lateinit var collectButton: ImageButton
    private lateinit var commentRecyclerViewPosts: RecyclerView
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var commentButton: FloatingActionButton
    private lateinit var comments: ArrayList<CommentItem>
    private lateinit var locations: List<LocationItem>
    private lateinit var locationNames : List<String>
    private var commentsIdList: List<String>? = null

    private lateinit var binding: ActivityPostDetailBinding

    var isLiked = false
    lateinit var likeNum : Integer
    var isCared = false
    var isCollected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userName = intent.getStringExtra("postOwnerUsername").toString()
        id = intent.getStringExtra("postId").toString()
        println(intent.getStringExtra("postOwnerAvatar"))
        avatarUri = Uri.parse(intent.getStringExtra("postOwnerAvatar"))

        initView()
        fetchPostFromServer()
        locations = fetchLocationItemsFromServer(locationNames)
        locationsUI.layoutManager=LinearLayoutManager(this)
        locationAdapter = LocationAdapter(locations)
        locationsUI.adapter = locationAdapter
        if (commentsIdList != null) {
            comments = fetchCommentsFromServer()
            commentRecyclerViewPosts.layoutManager= LinearLayoutManager(this)
            commentAdapter = CommentAdapter(comments)
            commentRecyclerViewPosts.adapter = commentAdapter
        }
        initEvent()
    }

    private fun fetchPostFromServer(){
        val thread = Thread{
            val response = Client.getPost(id)
            if (response.success==true){
                println("地区列表："+response.post)
                title.text = response.post!!.title
                content.text = response.post.content
                likeNum = response.post.likeNum
                commentsIdList = response.post.reviews
                locationNames = response.post.locations!!
            }
        }
        thread.start()
        thread.join()
    }

    private fun fetchCommentsFromServer(): ArrayList<CommentItem> {
        // 在此处通过网络请求或其他方式获取评论列表数据
        // 将数据转换为对象的列表并返回
        val comments = ArrayList<CommentItem>()

        val thread = Thread {
            for (id in commentsIdList!!)
            {
                val response1 = Client.getPostComment(id)
                if (response1.success == true){
                    val response2 = Client.getUserInfo2(response1.review!!.username)
                    val len = response2.userInfo?.avatarUrl!!.length
                    val tmp = CommentItem(
                        response2.userInfo.username,
                        Uri.parse(response2.userInfo.avatarUrl),
                        response2.userInfo.nickname?: "无昵称",
                        response1.review.date,
                        response1.review.content
                    )
                    comments.add(tmp)
                }
            }
        }
        thread.start()
        thread.join()

        return comments
    }

    private fun initView() {
        nickName = binding.postTextViewNickname
        avatar = binding.postImageViewAvatar
        careButton =binding.careButton
        title = binding.postTextViewTitle
        content = binding.postTextViewContent
        locationsUI = binding.locations
        likeButton = binding.likeButton
        likeButton.setImageResource(R.drawable.baseline_thumb_up_off_alt_24)
        collectButton = binding.collectButton
        commentRecyclerViewPosts = binding.recyclerCommentView
        commentButton = binding.commentButton

        nickName.text = intent.getStringExtra("postOwnerNickname")
        Glide.with(this)
            .load(avatarUri)
            .into(avatar)
    }

    private fun initEvent(){
        val toolbar : Toolbar = binding.include.idToolbar
        toolbar.setTitle("帖子详情")
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        likeButton.setOnClickListener {
            if(isLiked)
                likeButton.setImageResource(R.drawable.baseline_thumb_up_off_alt_24)

            else {
                likeButton.setImageResource(R.drawable.baseline_thumb_up_alt_24)
            }
            isLiked = !isLiked
        }

        collectButton.setOnClickListener {
            if(isCollected)
                collectButton.setImageResource(R.drawable.baseline_star_border_24)

            else {
                collectButton.setImageResource(R.drawable.baseline_star_24)
            }
            isCollected = !isCollected
        }

        careButton.setOnClickListener {
            if(isCared) {
                careButton.setText("关注")
            }

            else {
                careButton.setText("取消关注")
            }
            isCared = !isCared
        }

        commentButton.setOnClickListener {
            showCommentDialog()
        }

        avatar.setOnClickListener{
            val context = avatar.context
            val others = Others(nickName.text.toString(),userName,Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                        resources.getResourcePackageName(R.drawable.baseline_person_48) + '/' +
                        resources.getResourceTypeName(R.drawable.baseline_person_48) + '/' +
                        resources.getResourceEntryName(R.drawable.baseline_person_48)
                )
            )
            // 执行跳转逻辑，启动 OtherPageActivity，并传递必要的数据
            val intent = Intent(context, OtherPageActivity::class.java)
            intent.putExtra("friendUsername", others.ownerUserName)
            context.startActivity(intent)
        }
    }

    private fun fetchLocationItemsFromServer(locs: List<String>): List<LocationItem> {
        var tmp = ArrayList<LocationItem>()
        val thread = Thread {
            for (i in locs) {
                val response = Client.getLocationInfo(i)
                if (response.success) {
                    println(response.locationInfo.locationPictureUrl)
                    println(response.locationInfo.locationDetail)
                    tmp.add(LocationItem(
                        (serverUrl + response.locationInfo.locationPictureUrl).toUri(),
                        response.locationInfo.locationDetail,
                        "1",
                        i)
                    )
                }
            }
        }
        thread.start()
        thread.join()
        return tmp
    }

    private fun showCommentDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("发表评论")
        var content: String
        // 创建评论内容的输入框
        val commentEditText = EditText(this)
        commentEditText.setPadding(16, 16, 16, 16)
        commentEditText.maxLines = 5
        commentEditText.gravity = Gravity.TOP or Gravity.START
        builder.setView(commentEditText)
        builder.setPositiveButton("发表") {
                _, _ ->
            content = commentEditText.text.toString()
            val Time = SimpleDateFormat("yyyy年MM月dd日HH时mm分", Locale.getDefault())
            val timecurrentTimeMillis = System.currentTimeMillis()
            comments.add(
                CommentItem(
                    MainActivity.userName,
                    Uri.parse(
                        ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                                resources.getResourcePackageName(R.drawable.baseline_person_48) + '/' +
                                resources.getResourceTypeName(R.drawable.baseline_person_48) + '/' +
                                resources.getResourceEntryName(R.drawable.baseline_person_48)),
                    "我的昵称",
                    Time.format(timecurrentTimeMillis),
                    content
                )
            )
            commentAdapter = CommentAdapter(comments)
            commentRecyclerViewPosts.adapter = commentAdapter
        }
        builder.setNegativeButton("取消") {
                dialog, _ -> dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}