package com.example.travelplanner.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places_table")
data class PlacesData (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "Title") var title: String,
    @ColumnInfo(name = "Description") var description: String,
    @ColumnInfo(name = "Image") var image: String,
    @ColumnInfo(name = "Date") var date: String,
    @ColumnInfo(name = "Location") var location: String,
    @ColumnInfo(name = "Latitude") var latitude: Double,
    @ColumnInfo(name = "Longitude") var longitude: Double,

    )