package com.example.client.ui

import android.content.ContentResolver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.databinding.ActivityFansorCaresBinding
import com.example.client.databinding.ActivityOtherPageBinding
import com.example.client.programLogic.bean.Others
import com.example.client.ui.component.PostAdapter
import com.example.client.ui.component.PostItem
import com.google.android.material.tabs.TabLayout

class OtherPageActivity : AppCompatActivity() {
    private lateinit var recyclerViewPosts: RecyclerView
    private lateinit var PostAdapter: PostAdapter

    private lateinit var fansTextView: TextView
    private lateinit var userName: TextView
    private lateinit var nickname: TextView
    private lateinit var birthdayAndGender: TextView
    private lateinit var sign: TextView
    private lateinit var binding: ActivityOtherPageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_page)

        binding = ActivityOtherPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar : Toolbar = binding.include.idToolbar
        toolbar.setTitle("Ta的主页")
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        initView()
        val tabLayout: TabLayout = binding.tabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Ta的发帖"))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val selectedTabText = it.text.toString()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        recyclerViewPosts.layoutManager= LinearLayoutManager(this)

        val posts = fetchPostsFromServer()
        PostAdapter = PostAdapter(posts)
        recyclerViewPosts.adapter = PostAdapter

    }

    private fun fetchPostsFromServer(): List<PostItem> {
        // 在此处通过网络请求或其他方式获取帖子列表数据
        // 将数据转换为PostItem对象的列表并返回
        val posts = ArrayList<PostItem>()

        // 示例帖子1
        val post1 = PostItem("这是示例帖子1的标题",
            Others(),
            Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                        resources.getResourcePackageName(R.drawable.baseline_person_48) + '/' +
                        resources.getResourceTypeName(R.drawable.baseline_person_48) + '/' +
                        resources.getResourceEntryName(R.drawable.baseline_person_48)),
            "这是示例帖子2的内容",
            "2023-12-27 11:00 AM",
            "11"
        )
        posts.add(post1)

        // 示例帖子2
        val post2 = PostItem("这是示例帖子2的标题",
            Others(),
            Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                        resources.getResourcePackageName(R.drawable.baseline_person_48) + '/' +
                        resources.getResourceTypeName(R.drawable.baseline_person_48) + '/' +
                        resources.getResourceEntryName(R.drawable.baseline_person_48)),
            "这是示例帖子2的内容",
            "2023-12-27 11:00 AM",
            "22"
        )
        posts.add(post2)

        return posts
    }

    private fun initView(){
        recyclerViewPosts = binding.recyclerView
        fansTextView = binding.fansTextView
        userName = binding.userNameTextView
        nickname = binding.nicknameTextView
        birthdayAndGender = binding.birthdayTextView
        sign = binding.signTextView

        userName.setText(intent.getStringExtra("friendUsername"))
    }
}