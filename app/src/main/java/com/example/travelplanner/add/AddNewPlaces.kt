package com.example.travelplanner.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.travelplanner.R
import kotlinx.android.synthetic.main.activity_add_new_places.*

class AddNewPlaces : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_places)
        setSupportActionBar(myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        myToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}