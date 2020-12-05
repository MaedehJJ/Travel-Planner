package com.example.travelplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ActivityNavigator
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.travelplanner.add.AddNewPlaces
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addNewPlacesFAB.setOnClickListener {
            startActivity(Intent(this , AddNewPlaces::class.java))
            overridePendingTransition(R.anim.to_right, R.anim.from_left)
        }
    }
}