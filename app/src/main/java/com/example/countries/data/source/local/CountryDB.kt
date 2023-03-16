package com.example.countries.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.countries.data.Country

@Database(entities = [Country::class], version = 1, exportSchema = false)
abstract class CountryDB: RoomDatabase() {

    abstract fun countriesDao(): CountryDao
    companion object{
        @Volatile
        private var INSTANCE: CountryDB? = null

        fun getCountryDB(context: Context): CountryDB {
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CountryDB::class.java,
                        "countries.db   "
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}