package com.kenshi.newsapiclient.data.repository.dataSource

import com.kenshi.newsapiclient.data.model.Article

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(article: Article)
}