package com.example.countries.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.countries.data.Country

@Dao
interface CountryDao {
    @Query("SELECT * from countries")
    fun getAll(): List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<Country>)

    @Query("DELETE from countries")
    suspend fun deleteAll()
}