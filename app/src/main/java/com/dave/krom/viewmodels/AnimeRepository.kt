package com.dave.krom.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import com.dave.krom.data.DbAnimeDataList
import com.dave.krom.sqlite.DatabaseHelper

class AnimeRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun insertData(malId:Int, json:String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put("malId", malId)
        values.put("json", json)

        return db.insert("anime", null, values)
    }

    @SuppressLint("Range")
    fun getAllData(): List<DbAnimeDataList> {
        val db = dbHelper.readableDatabase
        val data = mutableListOf<DbAnimeDataList>()

        val cursor: Cursor? = db.rawQuery("SELECT * FROM your_table_name", null)

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getLong(it.getColumnIndex("id"))
                    val malId = it.getString(it.getColumnIndex("malId"))
                    val json = it.getString(it.getColumnIndex("json"))

                    data.add(DbAnimeDataList(id,malId,json))
                } while (it.moveToNext())
            }
        }

        return data
    }


}