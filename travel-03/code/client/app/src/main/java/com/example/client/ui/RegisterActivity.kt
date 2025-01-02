package com.example.client.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.client.R
import com.example.client.programLogic.bean.User.User
import com.example.client.programLogic.bean.User.UserResponseFailedReason
import com.example.client.programLogic.netSever.Client

class RegisterActivity : AppCompatActivity() {
    private var eEtUserName: EditText? = null
    private var eEtPassword: EditText? = null
    private var eEtRePassword: EditText? = null
    private var eBtnRegister: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
        initEvent()
        title = "注册"
        setUpToolBar()
    }

    private fun setUpToolBar() {
        val toolbar = findViewById<Toolbar>(R.id.id_toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initEvent() {
        eBtnRegister!!.setOnClickListener {
            if (eEtPassword!!.text.toString() == eEtRePassword!!.text.toString()){
                Registe()
            }
            else{
                Toast.makeText(applicationContext, StringBuilder("密码不一致"), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView() {
        eEtUserName = findViewById(R.id.id_userName)
        eBtnRegister = findViewById(R.id.toRegist_button)
        eEtPassword = findViewById(R.id.id_Password)
        eEtRePassword = findViewById(R.id.id_rePassword)
    }

    private fun Registe(){

        val thread = Thread {
            val response =
                Client.addUser(User(eEtUserName!!.text.toString(), eEtPassword!!.text.toString()))

            if (response.success!!) {
                runOnUiThread {
                    Toast.makeText(applicationContext, StringBuilder("注册成功"), Toast.LENGTH_SHORT).show()
                }
                Thread.currentThread().interrupt()
            }
            else {
                runOnUiThread{
                    if(response.userResponseFailedReason== UserResponseFailedReason.USERNAME_ALREADY_EXIST) {
                        Toast.makeText(applicationContext, "用户名已存在", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        thread.start()
    }
}