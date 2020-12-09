package com.example.travelplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.ActivityNavigator
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelplanner.adapter.PlacesListAdapter
import com.example.travelplanner.add.AddNewPlaces
import com.example.travelplanner.database.PlacesData
import com.example.travelplanner.viewModel.PlacesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mPlacesViewModel : PlacesViewModel by viewModels()
    private lateinit var adapter: PlacesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpRecyclerView()
        getPlacesListFromDB()

        addNewPlacesFAB.setOnClickListener {
            startActivity(Intent(this , AddNewPlaces::class.java))
            overridePendingTransition(R.anim.to_right, R.anim.from_left)
        }
    }
    private fun getPlacesListFromDB(){
        mPlacesViewModel.getAllData.observe(this , Observer { data ->
            adapter.setData(data as ArrayList)
        })


    }
    private fun setUpRecyclerView(){
        adapter = PlacesListAdapter()
        myRecyclerView.adapter = adapter
        myRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}