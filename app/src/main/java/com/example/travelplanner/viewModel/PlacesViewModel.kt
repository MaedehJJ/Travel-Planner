package com.example.travelplanner.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.travelplanner.database.PlacesData
import com.example.travelplanner.database.PlacesDatabase
import com.example.travelplanner.repository.PlacesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlacesViewModel(application: Application) : AndroidViewModel(application) {
    private val placesDao = PlacesDatabase.getDataBase(application).placesDao()
    private val repository : PlacesRepository
    val getAllData : LiveData<List<PlacesData>>

    init {
        repository = PlacesRepository((placesDao))
        getAllData = repository.getAllData
    }

    fun insertData(placesData: PlacesData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(placesData)
        }
    }

    fun updateData(placesData: PlacesData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(placesData)
        }
    }

    fun deleteItem(placesData: PlacesData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(placesData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<PlacesData>> {
        return repository.searchDatabase(searchQuery)
    }
}