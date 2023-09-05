package com.dave.krom

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dave.krom.network_request.requests.RetrofitCalls
import com.dave.krom.viewmodels.AnimeViewModel

class Splash : AppCompatActivity() {

    private lateinit var viewModel: AnimeViewModel
    private val retrofitCalls = RetrofitCalls()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = AnimeViewModel(this.application)

        // Start the main activity after a delay
        Handler().postDelayed({
            retrofitCalls.getAnimeValues(this, viewModel)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

            }, 3000)
    }
}