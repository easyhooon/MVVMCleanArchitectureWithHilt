package com.kenshi.newsapiclient.data.api

import com.kenshi.newsapiclient.BuildConfig
import com.kenshi.newsapiclient.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {

    //this is the url end point
    @GET("/v2/top-headlines")
    //we need to add query parameters
    suspend fun getTopHeadlines(
        @Query("country")
        country:String,
        @Query("page")
        page:Int,
        @Query("apiKey")
        apiKey:String = BuildConfig.API_KEY
    ): Response<APIResponse>

    //this is the url end point
    @GET("/v2/top-headlines")
    //we need to add query parameters
    suspend fun getSearchedNews(
        @Query("country")
        country:String,
        @Query("q")
        searchQuery:String,
        @Query("page")
        page:Int,
        @Query("apiKey")
        apiKey:String = BuildConfig.API_KEY
    ): Response<APIResponse>
}