package com.example.travelplanner.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelplanner.R
import com.example.travelplanner.database.PlacesData
import kotlinx.android.synthetic.main.row_list.view.*

class PlacesListAdapter() : RecyclerView.Adapter<PlacesListAdapter.ViewHolder>() {

    private var list = ArrayList<PlacesData>()

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindViews(placesData: PlacesData) {
            itemView.rowTitle.text = placesData.title
            itemView.rowDescription.text = placesData.description
            itemView.rowImage.setImageURI(Uri.parse(placesData.image))
            itemView.rowDate.text = placesData.date
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlacesListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_list , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlacesListAdapter.ViewHolder, position: Int) {
        holder.bindViews(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun setData(placesData: ArrayList<PlacesData>){
        this.list = placesData
        this.notifyDataSetChanged()
    }
}