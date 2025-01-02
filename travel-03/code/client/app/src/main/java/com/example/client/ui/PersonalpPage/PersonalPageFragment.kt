package com.example.client.ui.PersonalpPage

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.client.R
import com.example.client.databinding.FragmentPersonalpageBinding
import com.example.client.programLogic.bean.Others
import com.example.client.programLogic.netSever.Client
import com.example.client.ui.FansorCaresActivity
import com.example.client.ui.LoginActivity
import com.example.client.ui.MainActivity
import com.example.client.ui.component.ContextToBrief
import com.example.client.ui.component.PostAdapter
import com.example.client.ui.component.PostItem
import com.google.android.material.tabs.TabLayout
import okhttp3.internal.wait
import java.io.File
import java.io.FileOutputStream


class PersonalPageFragment : Fragment() {

    private lateinit var recyclerViewPosts: RecyclerView
    private lateinit var PostAdapter: PostAdapter
    private lateinit var infoButton: Button
    private lateinit var fansButton: Button
    var fansNum :Int = 0
    private lateinit var careButton: Button
    var caresNum :Int = 0
    private lateinit var exitButton: Button
    private lateinit var userName: TextView
    private lateinit var nickname: TextView
    private lateinit var birthdayAndGender: TextView
    private lateinit var sign: TextView
    private lateinit var avatar: ImageView
    private lateinit var file: File
    private lateinit var changed: Uri

    private lateinit var myPosts: List<String>
    private lateinit var myPost: List<PostItem>

    private var currentNickname = "hhy"
    private var currentBirthday = "2003-5-20"
    private var currentGender = "男"
    private var currentSign = "Android 要我命!"
    private lateinit var currentAvatarUri :Uri

    private lateinit var sharedPreferences :SharedPreferences
    private lateinit var editor:SharedPreferences.Editor

    private var _binding: FragmentPersonalpageBinding? = null

    private val binding get() = _binding!!

    @RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val personalPageViewModel =
            ViewModelProvider(this).get(PersonalPageViewModel::class.java)

        _binding = FragmentPersonalpageBinding.inflate(inflater, container, false)

        sharedPreferences = requireContext().getSharedPreferences("Mine",Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        initView()
        infoButton.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.edit_profile_dialog, null)
            val nicknameEditText = dialogView.findViewById<EditText>(R.id.nicknameEditText)
            val birthdayEditText = dialogView.findViewById<EditText>(R.id.birthdayEditText)
            val genderSpinner = dialogView.findViewById<Spinner>(R.id.genderSpinner)
            val signatureEditText = dialogView.findViewById<EditText>(R.id.signatureEditText)

            val confirmButton = dialogView.findViewById<Button>(R.id.confirmButton)
            val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)

            val dialogBuilder = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setTitle("修改个人信息")
            nicknameEditText.setText(currentNickname)
            birthdayEditText.setText(currentBirthday)
            signatureEditText.setText(currentSign)

            val alertDialog = dialogBuilder.create()

            confirmButton.setOnClickListener {
                currentNickname = nicknameEditText.text.toString()
                currentBirthday = birthdayEditText.text.toString()
                currentGender = genderSpinner.selectedItem.toString()
                currentSign = signatureEditText.text.toString()
                alertDialog.dismiss()
                updateInfo()
                refresh()
                refresh()
            }
            cancelButton.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
        exitButton.setOnClickListener {

            MainActivity.userName = ""
            editor.clear()
            editor.apply()

            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        refresh()

        val tabLayout: TabLayout = binding.tabLayout
        tabLayout.addTab(tabLayout.newTab().setText("我的发帖"))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    if (it.text.toString()=="我的发帖") {
                        myPost = fetchPostsFromServer()
                        PostAdapter = PostAdapter(myPost)
                    }
                    recyclerViewPosts.adapter = PostAdapter
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        val root: View = binding.root

        recyclerViewPosts.layoutManager = LinearLayoutManager(requireContext())
        myPost = fetchPostsFromServer()
        PostAdapter = PostAdapter(myPost)
        recyclerViewPosts.adapter = PostAdapter

        return root
    }

    private fun fetchPostsFromServer(): List<PostItem> {
        // 在此处通过网络请求或其他方式获取帖子列表数据
        // 将数据转换为PostItem对象的列表并返回
        val posts = ArrayList<PostItem>()
        val thread = Thread {
            for (i in myPosts) {
                val response1 = Client.getPost(i)
                if (response1.success){
                    val post = response1.post
                    if (post != null) {
                        val response2 = Client.getUserInfo2(post.owner)
                        posts.add(
                            PostItem(
                                post.title,
                                Others(
                                    response2.userInfo!!.nickname!!,
                                    response2.userInfo.username,
                                    response2.userInfo.avatarUrl!!.toUri()
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
        thread.start()
        thread.join()
        return posts
    }

    fun initView() {
        userName = binding.userNameTextView
        infoButton = binding.infoButton
        nickname = binding.nicknameTextView
        recyclerViewPosts = binding.recyclerView
        fansButton = binding.fansButton
        careButton = binding.careButton
        exitButton = binding.exitButton
        birthdayAndGender = binding.birthdayTextView
        sign = binding.signTextView
        avatar = binding.imageViewAvatar

        currentNickname = sharedPreferences.getString("nickname","").toString()
        currentAvatarUri = Uri.parse(sharedPreferences.getString("avatar",Uri.EMPTY.toString()))
    }

    @SuppressLint("SetTextI18n")
    private fun refresh() {
        fectchUserInfo()
        userName.text = StringBuilder("用户名：" + MainActivity.userName)
        nickname.text = StringBuilder("昵称：$currentNickname")
        birthdayAndGender.text = StringBuilder("$currentBirthday  $currentGender")
        Glide.with(this)
            .load(currentAvatarUri)
            .into(avatar)
        sign.text = StringBuilder("个性签名：$currentSign")
        fansButton.text = "粉丝： $fansNum"
        careButton.text = "关注： $caresNum"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // onActivityResult() handles callbacks from the photo picker.
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            // Handle error
            return
        }
        when (requestCode) {
            1001 -> {
                var currentUri: Uri? = data?.data
                if (currentUri != null) {
                    changed = currentUri
                    val inputStream = requireActivity().contentResolver.openInputStream(currentUri)
                    file = File.createTempFile("image", null)
                    val outputStream = FileOutputStream(file)
                    inputStream?.copyTo(outputStream)
                    inputStream?.close()
                    outputStream.close()
                }
                return
            }
        }
    }

    fun fectchUserInfo(){
        val thread = Thread {
            val response1 = Client.getUserInfo1(MainActivity.userName)
            val response2 = Client.getUserInfo2(MainActivity.userName)

            currentGender = response1.gender ?: "保密"
            currentSign = response1.signature ?: "无个性签名哦"
            currentBirthday = response1.birthday ?: "暂无生日"
            if (response2.success == true){
                currentNickname = response2.userInfo?.nickname!!
                val len = response2.userInfo.avatarUrl.toString().length
                currentAvatarUri = Uri.parse(response2.userInfo.avatarUrl.toString())
                myPosts = response2.userInfo.myPosts
            }
        }
        thread.start()
        thread.join()
    }

    private fun updateInfo() {
        println(currentAvatarUri)
        //println(filePath)
        val thread = Thread {
            val response = Client.updateUserInfo(
                MainActivity.userName,
                currentNickname,
                currentBirthday,
                currentGender,
                currentSign
            )
            if (response.success == true){
            }
        }
        thread.start()
    }
}