package com.example.client.ui

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.client.R
import com.example.client.databinding.ActivityFansorCaresBinding
import com.example.client.programLogic.bean.Others
import com.example.client.programLogic.netSever.Client
import com.example.client.ui.component.ContextToBrief
import com.example.client.ui.component.PersonAdapter
import com.example.client.ui.component.PostItem

class FansorCaresActivity : AppCompatActivity() {
    private lateinit var PersonAdapter: PersonAdapter
    private lateinit var binding: ActivityFansorCaresBinding
    private lateinit var FansOrCares: ArrayList<Others>

    private var recyclerViewPerson: RecyclerView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFansorCaresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ForC = intent.getStringExtra("ForC")
        val persons = intent.getStringArrayListExtra("persons")

        val toolbar : Toolbar = binding.include.idToolbar
        if (ForC == "F") {
            toolbar.setTitle("我的粉丝")
        }
        else if(ForC == "C"){
            toolbar.setTitle("我的关注")
        }
        if (persons!= null) {
            FansOrCares = fetchPersonsFromServer(persons)
            println(persons)
            println(FansOrCares)
            recyclerViewPerson = binding.friendListRecyclerView
            recyclerViewPerson!!.layoutManager = LinearLayoutManager(this)
            PersonAdapter = PersonAdapter(FansOrCares)
            recyclerViewPerson!!.adapter = PersonAdapter
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun fetchPersonsFromServer(personId: ArrayList<String>): ArrayList<Others> {
        val persons = ArrayList<Others>()
        val thread = Thread{
            for (id in personId) {
                val response = Client.getUserInfo2(id)
                if (response.success == true) {
                    val len = response.userInfo?.avatarUrl!!.length
                    val tmp = Others(
                        response.userInfo.nickname ?: "无昵称",
                        response.userInfo.username,
                        Uri.parse(response.userInfo.avatarUrl)
                    )
                    persons.add(tmp)
                }
            }
        }
        thread.join()
        thread.start()
        return persons
    }
}