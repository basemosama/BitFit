package com.codepath.bitfit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codepath.bitfit.database.dao.SleepDao
import com.codepath.bitfit.database.typeconverters.DateTypeConverter
import com.codepath.bitfit.model.SleepItem


@Database(entities = [SleepItem::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class SleepDatabase : RoomDatabase() {
    abstract fun sleepDao(): SleepDao


    companion object {
        private const val DATABASE_NAME = "sleep_database"
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: SleepDatabase? = null

        fun getDatabase(context: Context): SleepDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SleepDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }



}
