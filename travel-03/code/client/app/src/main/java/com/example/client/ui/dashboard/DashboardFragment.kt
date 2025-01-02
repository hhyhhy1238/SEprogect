package com.example.client.ui.dashboard

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.databinding.FragmentDashboardBinding
import com.example.client.programLogic.bean.Travel.TravelPlan
import com.example.client.programLogic.bean.User.UserResponseFailedReason
import com.example.client.programLogic.netSever.Client
import com.example.client.programLogic.netSever.serverUrl
import com.example.client.ui.MainActivity
import com.example.client.ui.appContext
import com.example.client.ui.component.LocationAdapter
import com.example.client.ui.component.LocationItem
import kotlin.properties.Delegates

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var startLocation: Spinner
    private lateinit var desLocation: Spinner
    private lateinit var textTitle: TextView
    private lateinit var textContent: RecyclerView
    private lateinit var locationsAdapter: LocationAdapter
    private lateinit var addButton: Button
    private lateinit var getLocationsButton: Button
    private lateinit var travelPlanId: String
    private val binding get() = _binding!!
    private lateinit var locations : List<String>
    private lateinit var locationItems : List<LocationItem>

    @RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        initView()
        initEvent()
        textContent.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        textContent.layoutManager=LinearLayoutManager(requireContext())
        val root: View = binding.root
        return root
    }

    private fun initView() {
        textTitle = binding.textTitle
        textContent = binding.locations
        startLocation = binding.start
        desLocation = binding.dst
        getLocationsButton = binding.getLocations
        addButton = binding.addAndShareButton
    }

    @SuppressLint("MissingInflatedId", "InflateParams")
    private fun initEvent(){
        addButton.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.edit_post_dialog,null)
            val titleEditText = dialogView.findViewById<EditText>(R.id.titleEditText)
            val contentEditText = dialogView.findViewById<EditText>(R.id.contentEditText)
            val confirmButton = dialogView.findViewById<Button>(R.id.confirmButton)
            val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)

            val dialogBuilder = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setTitle("分享内容编辑")
            val alertDialog = dialogBuilder.create()
            var title = "这是我的标题"
            var content = "这个旅游规划太好了！"
            confirmButton.setOnClickListener {
                title = titleEditText.text.toString()
                content = contentEditText.text.toString()
                if(title.equals("")) {
                    Toast.makeText(appContext,StringBuilder("请输入标题"),Toast.LENGTH_SHORT).show()
                }
                else {
                    alertDialog.dismiss()
                    Toast.makeText(appContext,StringBuilder("添加成功"),Toast.LENGTH_SHORT).show()
                    addAndSharePost(title, content)
                }
            }
            cancelButton.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.show()
        }

        getLocationsButton.setOnClickListener {
            val travelPlan = TravelPlan(startLocation.selectedItem.toString(),desLocation.selectedItem.toString())
            locations = fetchLocationsFromServer(travelPlan)
            locationItems = fetchLocationItemsFromServer(locations)
            locationsAdapter = LocationAdapter(locationItems)
            textContent.adapter = locationsAdapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            // Handle error
            return
        }
        when (requestCode) {
            1002 -> {
//                var currentUri: Uri? = data?.data
//                if (currentUri != null) {
//                    avatar.setImageURI(currentUri)
//                    currentAvatarUri = currentUri
//                }
                return
            }
        }
    }
    private fun fetchLocationsFromServer(t: TravelPlan): List<String> {
        var tmp = ArrayList<String>()
        val thread = Thread {
            val response = Client.makeTravelPlan(t)
            if (response.success) {
                println(response.travelPlanDetails!!.locations !!)
                tmp = response.travelPlanDetails.locations as ArrayList<String>
                travelPlanId = response.travelPlanDetails.id
            }
        }
        thread.start()
        thread.join()
        return tmp
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

    private fun addAndSharePost(title: String,content: String): Boolean{
        var suc :Boolean =false
        val usrname = MainActivity.userName
        val thread = Thread{
            val response = Client.addPost("0",travelPlanId,usrname,title,content)
            suc=response.success
        }
        thread.start()
        thread.join()
        return suc
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}