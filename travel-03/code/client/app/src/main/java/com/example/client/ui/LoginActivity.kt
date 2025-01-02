package com.example.client.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.client.R
import com.example.client.programLogic.bean.StorageItem
import com.example.client.programLogic.bean.User.UserResponseFailedReason
import com.example.client.programLogic.netSever.Client
import com.example.client.programLogic.netSever.Client.login


class LoginActivity : AppCompatActivity() {
    private var eEtUserName: EditText? = null
    private var eEtPassword: EditText? = null
    private var eBtnLogin: Button? = null
    private var eTvRegister: TextView? = null
    private var state:Boolean = false
    private var storageItem:StorageItem =StorageItem("", "", Uri.EMPTY.toString(), false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        initEvent()
        val sharedPreferences = getSharedPreferences("Mine", MODE_PRIVATE)
        storageItem.username = sharedPreferences.getString("username","")!!
        storageItem.nickname = sharedPreferences.getString("nickname","")!!
        storageItem.avatar = sharedPreferences.getString("avatar",Uri.EMPTY.toString())!!
        storageItem.state = false
        //storageItem.state = sharedPreferences.getBoolean("state",false)

        if(storageItem.state) {
            Toast.makeText(applicationContext, StringBuilder("欢迎回来"), Toast.LENGTH_SHORT).show()
            toMainActivity()
        }
    }

    private fun initEvent() {
        eBtnLogin?.setOnClickListener {
            Login()
        }
        eTvRegister?.setOnClickListener { toRegisterActivity() }
    }

    private fun toRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun toMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("username", storageItem.username)
        startActivity(intent)
        finish()
    }

    private fun initView() {
        eEtUserName = findViewById(R.id.id_userName)
        eBtnLogin = findViewById(R.id.login_button)
        eEtPassword = findViewById(R.id.id_Password)
        eTvRegister = findViewById(R.id.register_button)
    }

    @SuppressLint("CommitPrefEdits")
    private fun Login(){

        val thread = Thread { // 在这里执行登录操作
            val response = login(eEtUserName!!.text.toString(),eEtPassword!!.text.toString())
            if (response.success!!) {
                runOnUiThread {
                    Toast.makeText(applicationContext, StringBuilder("登录成功"), Toast.LENGTH_SHORT).show()

                    println(response.userInfo!!.username)

                    storageItem.username = response.userInfo.username
                    storageItem.nickname = response.userInfo.nickname!!
                    val len = response.userInfo.avatarUrl.toString().length
                    storageItem.avatar = response.userInfo.avatarUrl.toString()
                    System.out.println(storageItem.avatar)
                    storageItem.state = true

                    val sharedPreferences = getSharedPreferences("Mine", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("username", storageItem.username)
                    editor.putString("nickname", storageItem.nickname)
                    editor.putString("avatar", storageItem.avatar)
                    editor.putBoolean("state",true)
                    editor.apply()

                    toMainActivity()
                }
                Thread.currentThread().interrupt()
            }
            else {
                runOnUiThread{
                    if(response.userResponseFailedReason == UserResponseFailedReason.USER_DOES_NOT_EXIST) {
                        Toast.makeText(applicationContext, "用户名不存在", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(applicationContext, "密码错误", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        thread.start()
    }
}
