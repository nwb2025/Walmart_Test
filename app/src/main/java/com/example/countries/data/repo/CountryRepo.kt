package com.example.countries.data.repo

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.countries.data.Country
import com.example.countries.data.source.local.CountryDB
import com.example.countries.data.source.remote.CountryService
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.example.countries.LOG_TAG
import com.example.countries.WEB_SERVICE_URL

class CountryRepo(val app: Application) {
    val countriesData = MutableLiveData<List<Country>>()
    private val countriesDao = CountryDB.getCountryDB(app).countriesDao()

    init {
        Log.i(LOG_TAG, "Network available: ${isNetworkAvailable()}")
        CoroutineScope(Dispatchers.IO).launch {
            // first - try to retrieve data from local db
            val data = fetchLocalData()
            if (data.isEmpty()) {
                callApi()
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(app, "Fetching data from a local db", Toast.LENGTH_SHORT).show()
                }
                countriesData.postValue(data)
            }
        }
    }

    @WorkerThread
    private suspend fun callApi() {
        if (isNetworkAvailable()) {
            Log.i(LOG_TAG, "Calling web service")

            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            val service = retrofit.create(CountryService::class.java)

            // making a get request
            val response = service.getGifData()

            if (response.isSuccessful) {
                val items = response.body()
                if(items != null) {
                    saveDatatoLocalDB(items)
                    countriesData.postValue(items)
                }
            } else {
                Log.e("RETROFIT_ERROR", response.message().toString())
            }
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(app, "No network connection. Please check your network settings ", Toast.LENGTH_SHORT).show()
            }
            countriesData.postValue(emptyList())
        }
    }

    @WorkerThread
    private fun fetchLocalData(): List<Country> {
        return countriesDao.getAll()
    }

    private suspend fun saveDatatoLocalDB(countries: List<Country>) {
        countriesDao.insertCountries(countries)
    }

    fun refreshDataFromWeb() {
        CoroutineScope(Dispatchers.IO).launch {
            callApi()
        }
    }

    // check the status of the internet connection
    @Suppress("DEPRECATION")
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }
}