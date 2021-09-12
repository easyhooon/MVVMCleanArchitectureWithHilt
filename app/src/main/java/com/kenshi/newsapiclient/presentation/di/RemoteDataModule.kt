package com.kenshi.newsapiclient.presentation.di

import com.kenshi.newsapiclient.data.api.NewsAPIService
import com.kenshi.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.kenshi.newsapiclient.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    //this function should take an instance of NewsAPIService as a parameter
    //Because this NewsRemoteDataSourceImpl class need an instance of NewsAPIService as a constructor parameter
    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(
        newsAPIService: NewsAPIService
    ):NewsRemoteDataSource{
        return NewsRemoteDataSourceImpl(newsAPIService)
    }
    //Since we are planning to construct a NewsRemoteDataSourceImpl inside this new function we need
    //to provide that NewsAPIService dependency as a parameter

    //So, when we are creating the provider function, we need to define it as a parameter

}