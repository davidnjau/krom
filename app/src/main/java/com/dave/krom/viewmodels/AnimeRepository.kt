package com.dave.krom.viewmodels

import android.R.string
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.dave.krom.data.DbAnimeData
import com.dave.krom.data.DbAnimeDataList
import com.dave.krom.sqlite.DatabaseHelper
import com.google.gson.Gson
import com.google.gson.JsonObject


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
    fun getAllData(): ArrayList<DbAnimeDataList> {
        val db = dbHelper.readableDatabase
        val data = ArrayList<DbAnimeDataList>()

        val cursor: Cursor? = db.rawQuery("SELECT * FROM anime", null)

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getLong(it.getColumnIndex("id"))
                    val malId = it.getString(it.getColumnIndex("malId"))
                    val json = it.getString(it.getColumnIndex("json"))

                    val convertedObject =
                        Gson().fromJson(json, DbAnimeData::class.java)
                    if (convertedObject != null){
                        val imageUrl = convertedObject.images.jpg.image_url
                        val videoUrl = convertedObject.trailer.url
                        val title = convertedObject.title
                        data.add(DbAnimeDataList(id,malId,imageUrl,videoUrl,title))
                    }

                } while (it.moveToNext())
            }
        }



        return data
    }

    fun clearAllData() {
        val db = dbHelper.readableDatabase
        db.use { db ->
            // Execute SQL DELETE commands for each table to remove all rows
            db.execSQL("DELETE FROM anime")
            // Repeat for other tables as needed
        }
    }


}