package com.example.countries.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "countries")
data class Country(
    @PrimaryKey @ColumnInfo(name = "capital") @field:Json(name = "capital") val capital: String,
    @ColumnInfo(name = "code") @field:Json(name = "code") val code: String?,
    @Embedded @field:Json(name = "currency") val currency: Currency?,
    @ColumnInfo(name = "flag") @field:Json(name = "flag") val flag: String?,
    @Embedded @field:Json(name = "language") val language: Language?,
    @ColumnInfo(name = "name") @field:Json(name = "name") val name: String?,
    @ColumnInfo(name = "region") @field:Json(name = "region") val region: String?
)

data class Currency(
    @ColumnInfo(name = "curr_code") @field:Json(name = "code") val code: String?,
    @ColumnInfo(name = "curr_name") @field:Json(name = "name") val name: String?,
    @ColumnInfo(name = "symbol") @field:Json(name = "symbol") val symbol: String?
)

data class Language(
    @ColumnInfo(name = "lang_code") @field:Json(name = "code") val code: String?,
    @ColumnInfo(name = "lang_name") @field:Json(name = "name") val name: String?
)



