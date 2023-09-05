package com.dave.krom.data

import com.dave.krom.R

enum class UrlData(var message: Int) {
    BASE_URL(R.string.base_url),
    BASE_URL_IMAGE(R.string.base_url_image),
}

data class DbUploadRes(
    val details: String,
    val video:String,
    val image:String
)
data class DbImageResponse(
    val result:List<DbImageResResult>
)
data class DbImageResResult(
    val filename: String,
    val episode: Any?,
    val video:String,
    val image:String
)
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
    val malId:String,
    val imageUrl:String
)