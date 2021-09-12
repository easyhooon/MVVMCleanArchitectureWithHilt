package com.kenshi.newsapiclient.presentation.di

import android.app.Application
import com.kenshi.newsapiclient.domain.repository.NewsRepository
import com.kenshi.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.kenshi.newsapiclient.presentation.viewmodel.NewsViewModel
import com.kenshi.newsapiclient.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        application: Application,
        getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(
            application,
            getNewsHeadlinesUseCase
        )
    }
}