package com.kenshi.newsapiclient.domain.usecase

import com.kenshi.newsapiclient.data.model.Article
import com.kenshi.newsapiclient.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article){
        return newsRepository.deleteNews(article)

    }
}