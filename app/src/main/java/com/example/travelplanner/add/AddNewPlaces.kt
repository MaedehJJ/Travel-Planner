package com.example.travelplanner.add

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.travelplanner.MainActivity
import com.example.travelplanner.R
import com.example.travelplanner.database.PlacesData
import com.example.travelplanner.viewModel.PlacesViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_add_new_places.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddNewPlaces : AppCompatActivity(), View.OnClickListener {

    private val calendar: Calendar = Calendar.getInstance()
    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    var savedImageToInternalStorage: Uri? = null
    var latitude: Double = 0.0
    var logitude: Double = 0.0
    private val mPlacesViewModel: PlacesViewModel by viewModels()

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
        showDateInView()
        dateEt.setOnClickListener(this)
        addNewImageTxt.setOnClickListener(this)
        saveButton.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.dateEt -> {
                DatePickerDialog(
                    this@AddNewPlaces,
                    dateSetListener, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()

            }
            R.id.addNewImageTxt -> {
                Toast.makeText(this, "add New image", Toast.LENGTH_LONG).show()
                val picDialog = AlertDialog.Builder(this)
                picDialog.setTitle("Select a Photo")
                val picDialogItems = arrayOf("Select from Gallery", "Capture new photo")
                picDialog.setItems(picDialogItems) { dialog, which ->
                    when (which) {
                        0 -> choosePhotoFromGallery()
                        1 -> takePhotoFromCamera()
                    }
                }
                picDialog.show()
            }
            R.id.saveButton -> {
                saveToDatabase()
                startActivity(Intent(this , MainActivity::class.java))
            }
        }
    }

    private fun saveToDatabase() {
        if (titleEt.text.isNotEmpty() && descriptionEt.text.isNotEmpty() && locationEt.text.isNotEmpty() && savedImageToInternalStorage != null) {
            val newPlace = PlacesData(
                id = 0,
                title = titleEt.text.toString(),
                description = descriptionEt.text.toString(),
                image = savedImageToInternalStorage.toString(),
                date = dateEt.text.toString(),
                location = locationEt.text.toString(),
                latitude = latitude,
                longitude = logitude
            )
            mPlacesViewModel.insertData(newPlace)
            Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(this, "Fields Can not be Empty", Toast.LENGTH_LONG).show()
        }


    }

    private fun takePhotoFromCamera() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(intent, CAMERA)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRationalDialogForPermissions()
                }
            }).onSameThread()
            .check()
    }

    private fun choosePhotoFromGallery() {
        Dexter.withActivity(this).withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report!!.areAllPermissionsGranted()) {
                    val galleryIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, GALLERY)
                }

            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                showRationalDialogForPermissions()

            }

        }).onSameThread().check()
    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("You need to grant permissions in order to select an Image from Gallery")
            .setPositiveButton("Go To Settings") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }

            }.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun showDateInView() {
        val format = "dd.MM.yyyy"
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        dateEt.setText(simpleDateFormat.format(calendar.time).toString())

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY) {
                if (data != null) {
                    val contentURI = data.data
                    try {
                        val selectedImage =
                            MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                        savedImageToInternalStorage = saveImageToInternalStorage(selectedImage)
                        wonderImage.setImageBitmap(selectedImage)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(this, "Failed to load the Image", Toast.LENGTH_LONG).show()
                    }
                }
            } else if (requestCode == CAMERA) {
                val thumbnail: Bitmap = data?.extras?.get("data") as Bitmap
                savedImageToInternalStorage = saveImageToInternalStorage(thumbnail)
                wonderImage.setImageBitmap(thumbnail)
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Canceled", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap): Uri {
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Uri.parse(file.absolutePath)
    }

    companion object {
        private const val GALLERY = 1
        private const val CAMERA = 2
        private const val IMAGE_DIRECTORY = "WonderLandImages"
    }
}