package com.example.travelplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.ActivityNavigator
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.travelplanner.add.AddNewPlaces
import com.example.travelplanner.database.PlacesData
import com.example.travelplanner.viewModel.PlacesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mPlacesViewModel : PlacesViewModel by viewModels()
    private lateinit var dataList: ArrayList<PlacesData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addNewPlacesFAB.setOnClickListener {
            startActivity(Intent(this , AddNewPlaces::class.java))
            overridePendingTransition(R.anim.to_right, R.anim.from_left)
        }
    }
    private fun getPlacesListFromDB(){
        mPlacesViewModel.getAllData.observe(this , Observer { data ->
            dataList.addAll(data)
        })


    }
}