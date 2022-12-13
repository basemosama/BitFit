package com.codepath.bitfit.database.dao

import androidx.room.*
import com.codepath.bitfit.model.SleepItem
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface SleepDao {

    @Query("SELECT * FROM sleep_table ORDER BY date DESC")
    fun getSleepData(): Flow<List<SleepItem>>

    @Query("SELECT * FROM sleep_table WHERE date = :date ")
    fun getSleepItem(date: Date): Flow<SleepItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSleepItem(item: SleepItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSleepData(items: List<SleepItem>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSleepItem(item: SleepItem)

    @Delete
    suspend fun deleteSleepItem(item: SleepItem)

    @Query("DELETE FROM sleep_table")
    suspend fun deleteAllSleepData()

}
