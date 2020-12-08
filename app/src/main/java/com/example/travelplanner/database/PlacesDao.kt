package com.example.travelplanner.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlacesDao {
    @Query("SELECT * FROM places_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<PlacesData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(placesData: PlacesData)

    @Update
    suspend fun updateData(placesData: PlacesData)

    @Delete
    suspend fun deleteItem(placesData: PlacesData)

    @Query("DELETE FROM places_table")
    suspend fun deleteAll()

    @Query("SELECT *  FROM places_table WHERE title LIKE :searchQuery ")
    fun searchDatabase(searchQuery: String): LiveData<List<PlacesData>>

}