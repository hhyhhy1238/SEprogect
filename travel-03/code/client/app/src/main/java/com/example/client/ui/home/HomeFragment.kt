package com.example.client.ui.home

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.databinding.FragmentHomeBinding
import com.example.client.programLogic.bean.Others
import com.example.client.programLogic.netSever.Client
import com.example.client.ui.component.ContextToBrief
import com.example.client.ui.component.PostAdapter
import com.example.client.ui.component.PostItem
import com.google.android.material.tabs.TabLayout


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    companion object{
        const val ARG_USER = "user"
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerViewPosts: RecyclerView
    private lateinit var PostAdapter: PostAdapter
    private lateinit var PsotList: List<PostItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val tabLayout: TabLayout = binding.tabLayout
        tabLayout.addTab(tabLayout.newTab().setText("主页"))

        recyclerViewPosts = binding.recyclerView
        recyclerViewPosts.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        recyclerViewPosts.layoutManager=LinearLayoutManager(requireContext())

        PsotList=fetchPostsFromServer()
        PostAdapter = PostAdapter(PsotList)
        recyclerViewPosts.adapter = PostAdapter
        return root
    }

    private fun fetchPostsFromServer(): List<PostItem> {
        // 在此处通过网络请求或其他方式获取帖子列表数据
        // 将数据转换为PostItem对象的列表并返回
        val posts = ArrayList<PostItem>()
        val thread = Thread {
            val response1 = Client.getPostList()
            if (response1.success) {
                for (i in response1.postIds) {
                    val response2 = Client.getPost(i)
                    if (response2.success){
                        val post = response2.post
                        if (post != null) {
                            val response3 = Client.getUserInfo2(post.owner)
                            posts.add(
                                PostItem(
                                    post.title,
                                    Others(
                                        response3.userInfo!!.nickname!!,
                                        response3.userInfo.username,
                                        response3.userInfo.avatarUrl!!.toUri()
                                    ),
                                    Uri.EMPTY,
                                    post.content,
                                    post.date,
                                    post.id
                                )
                            )
                        }
                    }
                }
            }
        }
        thread.start()
        thread.join()
        return posts
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}