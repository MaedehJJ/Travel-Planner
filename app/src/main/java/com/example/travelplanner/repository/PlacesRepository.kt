package com.example.travelplanner.repository

import androidx.lifecycle.LiveData
import com.example.travelplanner.database.PlacesDao
import com.example.travelplanner.database.PlacesData

class PlacesRepository(private val placesDao: PlacesDao) {

    val getAllData: LiveData<List<PlacesData>> = placesDao.getAllData()

    suspend fun insertData(placesData: PlacesData) {
        placesDao.insertData(placesData)
    }

    suspend fun updateData(placesData: PlacesData) {
        placesDao.updateData(placesData)
    }

    suspend fun deleteItem(placesData: PlacesData) {
        placesDao.deleteItem(placesData)
    }

    suspend fun deleteAll() {
        placesDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<PlacesData>> {
        return placesDao.searchDatabase(searchQuery)
    }

}