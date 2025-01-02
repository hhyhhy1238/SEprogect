package com.example.client.ui.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.client.R

class LocationAdapter (private val Locations:List<LocationItem>) :
    RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    private val isExpanded = mutableListOf<Boolean>()
    init {
        Locations.forEach { _ -> isExpanded.add(false) }
    }
    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageAdapter: ImageView = itemView.findViewById(R.id.locationImageViewAvatar)
        val textViewName: TextView = itemView.findViewById(R.id.locationTextView)
        val textViewDescription: TextView = itemView.findViewById(R.id.locationDescriptionTextView)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                isExpanded[position] = !isExpanded[position]
                notifyItemChanged(position)
            }
        }
        fun bind(locationItem: LocationItem){
            println(locationItem.image)
            Glide.with(itemView)
                .load(locationItem.image)
                .into(imageAdapter)
            textViewName.text = locationItem.name
            textViewDescription.text = locationItem.content
            textViewDescription.visibility = if (isExpanded[adapterPosition]) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Locations.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val locationItem = Locations[position]
        holder.bind(locationItem)
    }
}