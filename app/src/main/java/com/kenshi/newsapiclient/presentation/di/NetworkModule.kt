package com.kenshi.newsapiclient.presentation.di

import com.kenshi.newsapiclient.BuildConfig
import com.kenshi.newsapiclient.data.api.NewsAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    //create function to provide a retrofit instance
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        //we need to construct a retrofit instance here and return it
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsAPIService(retrofit: Retrofit):NewsAPIService{
        return retrofit.create(NewsAPIService::class.java)
    }
}

//when we were using pure dagger,
//we created component interfaces and listed these module there.

//But now, with dagger hilt, we have already created, in built set of components

//Unlike Dagger modules, we must annotate Hilt modules with
//@InstallIn to tell Hilt which Android class each module will be used or installed in.