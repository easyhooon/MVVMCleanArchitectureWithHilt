package com.kenshi.newsapiclient.domain.usecase

import com.kenshi.newsapiclient.data.model.Article
import com.kenshi.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {

    //do not need suspend keyword
    fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNews()
    }
}