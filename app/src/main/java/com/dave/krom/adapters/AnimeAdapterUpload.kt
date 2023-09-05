package com.dave.krom.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dave.krom.R
import com.dave.krom.ViewAnime
import com.dave.krom.data.DbAnimeDataList
import com.dave.krom.data.DbUploadRes
import com.dave.krom.helper.FormatterClassHelper
import com.squareup.picasso.Picasso

class AnimeAdapterUpload (
    private var dbChatList: ArrayList<DbUploadRes>,
    private val context: Context
): RecyclerView.Adapter<AnimeAdapterUpload.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view),
        View.OnClickListener{

        val imageView: ImageView
        val tvTextView: TextView

        init {
            imageView = view.findViewById(R.id.imageView)
            tvTextView = view.findViewById(R.id.tvTextView)

            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {

            val pos = adapterPosition
            val details = dbChatList[pos].details
            val video = dbChatList[pos].video

            FormatterClassHelper().saveSharedPreference(context, "details", details)
            FormatterClassHelper().saveSharedPreference(context, "videoUrl", video.toString())
            FormatterClassHelper().saveSharedPreference(context, "details", details.toString())

            val intent = Intent(context, ViewAnime::class.java)
            context.startActivity(intent)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_layout_upload, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = dbChatList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val imageUrl = dbChatList[position].image
        val text = dbChatList[position].details

        holder.tvTextView.text = text

        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.user_placeholder)
            .error(R.drawable.user_placeholder_error)
            .into(holder.imageView);

    }


}