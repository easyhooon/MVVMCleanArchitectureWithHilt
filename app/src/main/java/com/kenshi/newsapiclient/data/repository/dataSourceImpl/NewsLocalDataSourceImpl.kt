package com.kenshi.newsapiclient.data.repository.dataSourceImpl

import com.kenshi.newsapiclient.data.db.ArticleDAO
import com.kenshi.newsapiclient.data.model.Article
import com.kenshi.newsapiclient.data.repository.dataSource.NewsLocalDataSource

//in order to save data to the database we need a DAO instance.
//therefore, we need to provide it as constructor
class NewsLocalDataSourceImpl(
    private val articleDAO: ArticleDAO
) : NewsLocalDataSource{
    override suspend fun saveArticleToDB(article: Article) {
        articleDAO.insert(article)
    }
}