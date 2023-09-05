package com.dave.krom.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dave.krom.R
import com.dave.krom.ViewAnime
import com.dave.krom.data.DbAnimeDataList
import com.dave.krom.helper.FormatterClassHelper
import com.squareup.picasso.Picasso

class AnimeAdapter (
    private var dbChatList: ArrayList<DbAnimeDataList>,
    private val context: Context
): RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view),
        View.OnClickListener{

        val imageView: ImageView

        init {
            imageView = view.findViewById(R.id.imageView)
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {

            val pos = adapterPosition
            val id = dbChatList[pos].id
            val imageUrl = dbChatList[pos].imageUrl
            val title = dbChatList[pos].title
            val malId = dbChatList[pos].malId
            val videoUrl = dbChatList[pos].videoUrl
            val episode = dbChatList[pos].episode
            val episodes = episode?.toString() ?: ""



            FormatterClassHelper().saveSharedPreference(context, "id", id.toString())
            FormatterClassHelper().saveSharedPreference(context, "imageUrl", imageUrl.toString())
            FormatterClassHelper().saveSharedPreference(context, "title", title.toString())
            FormatterClassHelper().saveSharedPreference(context, "malId", malId.toString())
            FormatterClassHelper().saveSharedPreference(context, "videoUrl", videoUrl.toString())
            FormatterClassHelper().saveSharedPreference(context, "episodes", episodes.toString())

            val intent = Intent(context, ViewAnime::class.java)
            context.startActivity(intent)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_layout, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = dbChatList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val imageUrl = dbChatList[position].imageUrl

        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.user_placeholder)
            .error(R.drawable.user_placeholder_error)
            .into(holder.imageView);

    }


}