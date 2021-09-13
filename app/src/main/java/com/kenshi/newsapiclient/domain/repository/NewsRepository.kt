package com.kenshi.newsapiclient.domain.repository

import com.kenshi.newsapiclient.data.model.APIResponse
import com.kenshi.newsapiclient.data.model.Article
import com.kenshi.newsapiclient.data.util.Resource
import kotlinx.coroutines.flow.Flow

//repository interface
//we will definitely define abstract functions
interface NewsRepository {

    //Network
    //Total
    suspend fun getNewsHeadlines(country: String, page : Int): Resource<APIResponse>
    //Search
    suspend fun getSearchedNews(country: String, searchQuery:String, page : Int): Resource<APIResponse>

    //Local
    suspend fun saveNews(article: Article)
    //Get
    fun getSavedNews(): Flow<List<Article>>
    //Delete
    suspend fun deleteNews(article: Article)

}
//we will implement this in the NewsRepositoryImpl class
//Those two functions(getNewsHeadLine, getSearchedNews)
//we will use for network communication

