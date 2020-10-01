package com.example.mystore.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mystore.database.models.UserR

@Database(
    entities = [
        UserR::class
    ], version = 1, exportSchema = false
)

abstract class StoreDatabase : RoomDatabase() {

    abstract val storeDao: StoreDao

    companion object {

        @Volatile
        private var INSTANCE: StoreDatabase? = null

        fun getInstance(context: Context): StoreDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StoreDatabase::class.java,
                        "my_store_database"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}