package com.kenshi.newsapiclient.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

//Room DB 사용을 위한 entity 선언
@Entity(
    tableName = "articles"
)

//these properties can have null values, to avoid errors
data class Article(
    //PrimaryKey 선언을 위한 id value 선언
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("source")
    val source: Source?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?
):Serializable

//we only use this Article class with the room database