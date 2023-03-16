package com.example.countries.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.countries.data.repo.CountryRepo

class SharedViewModel(app: Application) : AndroidViewModel(app) {
    private val dataRepo = CountryRepo(app)
    val countriesData = dataRepo.countriesData
    //val selectedGif = MutableLiveData<GifData>()

    fun refreshData() {
        dataRepo.refreshDataFromWeb()
    }
}
