package com.kenshi.newsapiclient.data.repository.dataSourceImpl

import com.kenshi.newsapiclient.data.api.NewsAPIService
import com.kenshi.newsapiclient.data.model.APIResponse
import com.kenshi.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

//we have to provide String country and int page number from new function
//apiKey parameter is already provided

//also we will need an instance of NewsAPIService
//-> now we can call to getTopHeadlines function of the NewsAPIService
class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService
): NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse> {
        return newsAPIService.getTopHeadlines(country, page)
    }
}
