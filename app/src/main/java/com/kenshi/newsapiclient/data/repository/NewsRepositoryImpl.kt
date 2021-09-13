package com.kenshi.newsapiclient.data.repository

import com.kenshi.newsapiclient.data.model.APIResponse
import com.kenshi.newsapiclient.data.model.Article
import com.kenshi.newsapiclient.data.repository.dataSource.NewsLocalDataSource
import com.kenshi.newsapiclient.data.util.Resource
import com.kenshi.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.kenshi.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

//we need to add newly created newsLocalDataSource as a constructor parameter
class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
): NewsRepository {
    override suspend fun getNewsHeadlines(country : String, page : Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country, page))
    }
    //Now to implement this getNewsHeadlines function we will use that newly created responseToResult
    //
    //function. return responseToResource newsRemoteDataSource.getTopHeadlines
    //
    //newsRemoteDataSource.getTopHeadlines returns a Response object, we converted it into a resource object.


    //function to input the Response instance of type APIResponse returned from the api
    //and output a Resource instance of type APIResponse
    private fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        if(response.isSuccessful){
            response.body()?.let{ result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }


    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Resource<APIResponse> {
        return responseToResource(
            newsRemoteDataSource.getSearchedNews(country, searchQuery, page)
        )
    }

    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticleToDB(article)
    }

    override suspend fun deleteNews(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getSavedNews(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }
}