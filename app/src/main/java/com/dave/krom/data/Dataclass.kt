package com.dave.krom.data

import com.dave.krom.R

enum class UrlData(var message: Int) {
    BASE_URL(R.string.base_url),
}
data class DbAnime(
    val data:List<DbAnimeData>
)
data class DbAnimeData(
    val mal_id:Int,
    val images:DbAnimeImages,
    val trailer:DbAnimeTrailer,
    val title:String,
    val type:String,
    val source:String,
    val episodes:Int,
    val status:String,
    val airing:String,
    val duration:String,
    val rating:String,
    val popularity:Int,
    val synopsis:String,
    val year:String
)
data class DbAnimeImages(
    val jpg:DbAnimeJpgImageUrl
)
data class DbAnimeJpgImageUrl(
    val image_url:String
)
data class DbAnimeTrailer(
    val url:String
)

data class DbAnimeDataList(
    val id:Long,
    val mal_id:String,
    val json:String
)