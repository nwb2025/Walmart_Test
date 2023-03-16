package com.example.countries.data.source.remote

import com.example.countries.data.Country
import retrofit2.Response
import retrofit2.http.GET

interface CountryService {
    @GET("countries.json")
    suspend fun getGifData(): Response<List<Country>>
}