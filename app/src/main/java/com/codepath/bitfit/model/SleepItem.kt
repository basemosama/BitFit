package com.codepath.bitfit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "sleep_table")
data class SleepItem(
    @PrimaryKey
    val date : Date,
    val sleepTime:Float,
    val feeling:Int,
    val notes:String?
)
