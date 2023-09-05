package com.dave.krom

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.dave.krom.helper.FormatterClassHelper


class ViewAnime : AppCompatActivity() {

    private lateinit var videoView:VideoView
    private lateinit var tvTitle:TextView
    private lateinit var tvEpisodes:TextView
    private val formatterClass = FormatterClassHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_anime)

        videoView = findViewById(R.id.videoView)
        tvTitle = findViewById(R.id.tvTitle)
        tvEpisodes = findViewById(R.id.tvEpisodes)

        getData()

    }

    private fun getData() {

        val videoUrl = formatterClass.retrieveSharedPreference(this, "videoUrl")
        val title = formatterClass.retrieveSharedPreference(this, "title")
        val episodes = formatterClass.retrieveSharedPreference(this, "episodes")

        if (videoUrl != null && title != null && episodes != null){

            tvEpisodes.text = episodes
            tvTitle.text = title

            val videoUri = Uri.parse(videoUrl)
            videoView.setVideoURI(videoUri)

            // Create a MediaController and set it to the VideoView
            // Create a MediaController and set it to the VideoView
            val mediaController = MediaController(this)
            videoView.setMediaController(mediaController)
            mediaController.setAnchorView(videoView)

            // Start playing the video

            // Start playing the video
            videoView.start()

        }else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.pause()
    }


}