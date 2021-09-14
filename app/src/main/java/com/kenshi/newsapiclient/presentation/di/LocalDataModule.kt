package com.kenshi.newsapiclient.presentation.di

import com.kenshi.newsapiclient.data.db.ArticleDAO
import com.kenshi.newsapiclient.data.repository.dataSource.NewsLocalDataSource
import com.kenshi.newsapiclient.data.repository.dataSourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(articleDAO: ArticleDAO):NewsLocalDataSource{
        return NewsLocalDataSourceImpl(articleDAO)
    }
}