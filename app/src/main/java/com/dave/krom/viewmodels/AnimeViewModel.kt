package com.dave.krom.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dave.krom.data.DbAnimeDataList

class AnimeViewModel(application: Application):AndroidViewModel(application) {

    private val repository:AnimeRepository
    private val animeListLiveData = MutableLiveData<ArrayList<DbAnimeDataList>>()

    fun getAnimeListLiveData(): MutableLiveData<ArrayList<DbAnimeDataList>> {
        // You can call your getAnimeList() method here and update the LiveData
        // For demonstration purposes, I'll assume you have a method getAnimeListFromDatabase()
        // that fetches the data from a database or any other source.
        val animeList = getAnimeList()
        animeListLiveData.postValue(animeList) // Update the LiveData with the fetched data
        return animeListLiveData
    }

    init {
        repository = AnimeRepository(application.applicationContext)
    }

    fun insertData(malId:Int, json:String){
        repository.insertData(malId, json)
    }
    fun getAnimeList():ArrayList<DbAnimeDataList>{
        return repository.getAllData()
    }
    fun nukeDb(){
        repository.clearAllData()
    }

}