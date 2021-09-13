package com.kenshi.newsapiclient.data.repository.dataSource

import com.kenshi.newsapiclient.data.model.APIResponse
import retrofit2.Response

//inside this interface we will define abstract functions
//to communicate with the api

//we need to pass country and the page id from the viewmodel.
//to facilitate that process, let's define them as parameters
interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(country : String, page : Int): Response<APIResponse>

    suspend fun getSearchedNews(country : String, searchQuery:String, page : Int): Response<APIResponse>
}