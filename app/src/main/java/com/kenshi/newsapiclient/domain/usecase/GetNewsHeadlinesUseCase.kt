package com.kenshi.newsapiclient.domain.usecase

import com.kenshi.newsapiclient.data.model.APIResponse
import com.kenshi.newsapiclient.data.util.Resource
import com.kenshi.newsapiclient.domain.repository.NewsRepository

//Usecases have references to Repositories (by constructor parameter)
class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country: String, page : Int): Resource<APIResponse> {
        return newsRepository.getNewsHeadlines(country, page)
    }

}