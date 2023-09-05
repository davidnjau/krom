package com.dave.krom.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dave.krom.data.DbAnimeDataList

class AnimeViewModel(application: Application):AndroidViewModel(application) {

    private val repository:AnimeRepository

    init {
        repository = AnimeRepository(application.applicationContext)
    }

    fun insertData(malId:Int, json:String){
        repository.insertData(malId, json)
    }
    fun getAnimeList():List<DbAnimeDataList>{

        return repository.getAllData()
    }

}