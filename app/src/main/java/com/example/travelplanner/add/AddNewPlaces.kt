package com.example.travelplanner.add

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.travelplanner.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_add_new_places.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class AddNewPlaces : AppCompatActivity(), View.OnClickListener {
    private val calendar: Calendar = Calendar.getInstance()
    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_places)
        setSupportActionBar(myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        myToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            showDateInView()
        }
        dateEt.setOnClickListener(this)
        addNewImageTxt.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.dateEt -> {
                DatePickerDialog(this@AddNewPlaces,
                        dateSetListener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show()

            }
            R.id.addNewImageTxt -> {
                Toast.makeText(this , "add New image", Toast.LENGTH_LONG).show()
                val picDialog = AlertDialog.Builder(this)
                picDialog.setTitle("Select a Photo")
                val picDialogItems = arrayOf("Select from Gallery", "Capture new photo")
                picDialog.setItems(picDialogItems) { dialog, which ->
                    when (which) {
                        0 -> choosePhotoFromGallery()
                        1 -> captureAphoto()
                    }
                }
                picDialog.show()
            }
        }
    }

    private fun captureAphoto() {
        Toast.makeText(this, "Selected", Toast.LENGTH_LONG).show()
    }

    private fun choosePhotoFromGallery() {
        Dexter.withActivity(this).withPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report?.areAllPermissionsGranted()!!) {
                    Toast.makeText(this@AddNewPlaces, "Select a Picture from gallery", Toast.LENGTH_LONG).show()
                }

            }

            override fun onPermissionRationaleShouldBeShown(p0: MutableList<PermissionRequest>?, p1: PermissionToken?) {
                showRationalDialogForPermissions()

            }

        }).onSameThread().check()
    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this).setMessage("You need to grant permissions in order to select an Image from Gallery")
                .setPositiveButton("Grant Permissions") { _, _ ->
                    //choosePhotoFromGallery()
                    Toast.makeText(this, "Go To Setting", Toast.LENGTH_LONG).show()
                }.setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }.show()
    }

    private fun showDateInView() {
        val format = "dd.MM.yyyy"
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        dateEt.setText(simpleDateFormat.format(calendar.time).toString())

    }
}