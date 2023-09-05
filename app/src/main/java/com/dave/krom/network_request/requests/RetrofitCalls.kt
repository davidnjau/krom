package com.dave.krom.network_request.requests

import android.content.Context
import com.dave.krom.data.UrlData
import com.dave.krom.network_request.builder.RetrofitBuilder
import com.dave.krom.network_request.interfaces.Interface
import com.dave.krom.viewmodels.AnimeViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RetrofitCalls {

//    fun loginUser(context: Context, userLogin: UserLogin){
//
//        CoroutineScope(Dispatchers.Main).launch {
//
//            val job = Job()
//            CoroutineScope(Dispatchers.IO + job).launch {
//
//                startLogin(context, userLogin)
//
//            }.join()
//        }
//
//    }
//    private suspend fun startLogin(context: Context, userLogin: UserLogin) {
//
//
//        val job1 = Job()
//        CoroutineScope(Dispatchers.Main + job1).launch {
//
//            var progressDialog = ProgressDialog(context)
//            progressDialog.setTitle("Please wait..")
//            progressDialog.setMessage("Authentication in progress..")
//            progressDialog.setCanceledOnTouchOutside(false)
//            progressDialog.show()
//
//            var messageToast = ""
//            val job = Job()
//            CoroutineScope(Dispatchers.IO + job).launch {
//
//                val formatter = FormatterClass()
//                val baseUrl = context.getString(UrlData.BASE_URL.message)
//                val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)
//                try {
//
//                    val apiInterface = apiService.loginUser(userLogin)
//                    if (apiInterface.isSuccessful){
//
//                        val statusCode = apiInterface.code()
//                        val body = apiInterface.body()
//
//                        if (statusCode == 200 || statusCode == 201){
//
//                            if (body != null){
//
//                                val token = body.token
//                                val expires = body.expires
//
//                                formatter.saveSharedPreference(context, "token", token)
//                                formatter.saveSharedPreference(context, "expires", expires)
//
//                                getUserData(context)
//
//                            }else{
//                                messageToast = "Error: Body is null"
//                            }
//
//                        }else{
//                            messageToast = "Error: The request was not successful"
//                        }
//
//
//
//                    }else{
//                        apiInterface.errorBody()?.let {
//                            val errorBody = JSONObject(it.string())
//                            messageToast = errorBody.getString("message")
//                        }
//                    }
//
//
//                }catch (e: Exception){
//
//                    messageToast = "There was an issue with the server"
//                }
//
//
//            }.join()
//            CoroutineScope(Dispatchers.Main).launch{
//
//                progressDialog.dismiss()
//                Toast.makeText(context, messageToast, Toast.LENGTH_LONG).show()
//
//            }
//
//        }
//
//    }

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

