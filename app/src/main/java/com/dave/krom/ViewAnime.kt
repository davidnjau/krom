package com.dave.krom

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dave.krom.helper.FormatterClassHelper
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ViewAnime : AppCompatActivity() {

    private lateinit var imageView:ImageView
    private lateinit var tvTitle:TextView
    private lateinit var tvEpisodes:TextView
    private val formatterClass = FormatterClassHelper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_anime)

        imageView = findViewById(R.id.imageView)
        tvTitle = findViewById(R.id.tvTitle)
        tvEpisodes = findViewById(R.id.tvEpisodes)

        getData()



    }

    private fun getData() {

        val videoUrl = formatterClass.retrieveSharedPreference(this, "videoUrl")
        val title = formatterClass.retrieveSharedPreference(this, "title")
        val episodes = formatterClass.retrieveSharedPreference(this, "episodes")
        val imageUrl = formatterClass.retrieveSharedPreference(this, "imageUrl")



        if (videoUrl != null && title != null && episodes != null && videoUrl != ""){

            tvEpisodes.text = episodes
            tvTitle.text = title

            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .into(imageView);

            Toast.makeText(this, "Please wait as we process the url", Toast.LENGTH_LONG).show()

            CoroutineScope(Dispatchers.IO).launch {
                delay(2000)
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                startActivity(browserIntent)
            }






        }else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }




}