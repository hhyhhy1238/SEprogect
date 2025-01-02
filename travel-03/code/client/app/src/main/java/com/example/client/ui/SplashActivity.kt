package com.example.client.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.client.R

class SplashActivity : AppCompatActivity() {
    private var skipBtn: Button? = null
    private val Handler = Handler()
    private val Runnable = Runnable { toLoginActivity() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initview()
        initeEvent()
        Handler.postDelayed(Runnable, 5000)
    }

    private fun initeEvent() {
        skipBtn!!.setOnClickListener {
            toLoginActivity()
            Handler.removeCallbacks(Runnable)
        }
    }

    private fun initview() {
        skipBtn = findViewById(R.id.id_btn_skip)
    }

    fun toLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        Handler.removeCallbacks(Runnable)
    }
}