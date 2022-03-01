package com.faris.runningapp.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "running_table")
data class Run(
    var img: Bitmap? = null,
    //This is the time of run start
    var timestamp: Long = 0L,
    var avgSpeedInKph: Float = 0f,
    var distanceInMeters: Int = 0,
    //Total time of run
    var timeInMillis: Long = 0L,
    var caloriesBurned: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}