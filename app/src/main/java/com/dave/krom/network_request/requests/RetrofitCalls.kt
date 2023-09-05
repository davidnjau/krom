package com.dave.krom.network_request.requests

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import com.dave.krom.data.DbUploadRes
import com.dave.krom.data.UrlData
import com.dave.krom.network_request.builder.RetrofitBuilder
import com.dave.krom.network_request.interfaces.Interface
import com.dave.krom.viewmodels.AnimeViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File


class RetrofitCalls {

    fun upload(context: Context, imageFile: File)= runBlocking{
        startUpload(context, imageFile)
    }
    private suspend fun startUpload(context: Context, imageFile: File):ArrayList<DbUploadRes> {


        val dbUploadResList = ArrayList<DbUploadRes>()
        val job1 = Job()
        CoroutineScope(Dispatchers.Main + job1).launch {

            var progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please wait..")
            progressDialog.setMessage("Upload in progress..")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            var messageToast = ""
            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                val bitmap = BitmapFactory.decodeFile(imageFile.toString())

                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val imageInByte = baos.toByteArray()

                val requestFile: RequestBody = RequestBody.create(
                    "image/png".toMediaTypeOrNull(),
                    imageInByte)
                val body = MultipartBody.Part.createFormData(imageFile.name, "image.png", requestFile)


                val baseUrl = context.getString(UrlData.BASE_URL_IMAGE.message)
                val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)
                try {

                    val apiInterface = apiService.uploadImage(body)
                    if (apiInterface.isSuccessful){

                        val statusCode = apiInterface.code()
                        val body = apiInterface.body()

                        if (statusCode == 200 || statusCode == 201){

                            if (body != null){

                                messageToast = "Upload is successful"

                                val result = body.result
                                result.forEach {

                                    val filename = it.filename
                                    var episode = it.episode
                                    episode = episode?.toString() ?: ""

                                    var video = it.video
                                    var image = it.image

                                    val dbUploadRes = DbUploadRes(
                                        "$filename episode:$episode",
                                        video,
                                        image)
                                    dbUploadResList.add(dbUploadRes)

                                }

                            }else{
                                messageToast = "Error: Body is null"
                            }

                        }else{
                            messageToast = "Error: The request was not successful"
                        }



                    }


                }catch (e: Exception){
                    e.printStackTrace()

                    messageToast = "There was an issue with the server"
                }


            }.join()
            CoroutineScope(Dispatchers.Main).launch{

                progressDialog.dismiss()
                Toast.makeText(context, messageToast, Toast.LENGTH_LONG).show()

            }

        }

        return dbUploadResList

    }

    fun getAnimeValues(context: Context, viewModel: AnimeViewModel){
        CoroutineScope(Dispatchers.IO).launch { getAnime(context, viewModel) }
    }

    private suspend fun getAnime(context: Context, viewModel: AnimeViewModel) {

        val baseUrl = context.getString(UrlData.BASE_URL.message)
        val gson = Gson()


        val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)
        try {

            val apiInterface = apiService.getAnime()

            if (apiInterface.isSuccessful){
                val statusCode = apiInterface.code()
                val body = apiInterface.body()

                if (statusCode == 200 || statusCode == 201){

                    if (body != null){

                        val dataList = body.data

                        if (dataList.isNotEmpty()){
                            viewModel.nukeDb()
                        }

                        dataList.forEach {
                            val malId = it.mal_id
                            //Convert the entire object to Json and save
                            val dnAnimeObject = it
                            val json = gson.toJson(dnAnimeObject)
                            viewModel.insertData(malId, json)
                        }



                    }

                }
            }

        }catch (e: Exception){
            e.printStackTrace()
        }

    }


}

