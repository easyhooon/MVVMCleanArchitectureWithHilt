package com.kenshi.newsapiclient.domain.usecase

import com.kenshi.newsapiclient.data.model.APIResponse
import com.kenshi.newsapiclient.data.util.Resource
import com.kenshi.newsapiclient.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {


    suspend fun execute(country:String, searchQuery:String, page:Int): Resource<APIResponse> {
        return newsRepository.getSearchedNews(country, searchQuery, page)
    }
}