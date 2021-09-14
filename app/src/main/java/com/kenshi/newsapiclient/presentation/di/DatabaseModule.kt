package com.kenshi.newsapiclient.presentation.di

import android.app.Application
import androidx.room.Room
import com.kenshi.newsapiclient.data.db.ArticleDAO
import com.kenshi.newsapiclient.data.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideNewsDatabase(app: Application): ArticleDatabase {
        return Room.databaseBuilder(app, ArticleDatabase::class.java,"news_db")
            .fallbackToDestructiveMigration()
        //this will allow room to destructively replace database tables, if migrations
        //that would migrate old database schemas to the latest schema version are not found
        //기존 데이터베이스를 현재 버전으로 업그레이드 하기 위한 이전 경로를 찾을 수 없을때
        //이전 경로가 누락되었을 때 기존 데이터를 잃어도 괜찮다면 호출
        //이전 경로 없이 이전을 실행하려할때 데이터베이스의 테이블에서 모든 데이터를 영구적으로 삭제
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(articleDatabase: ArticleDatabase):ArticleDAO{
        return articleDatabase.getArticleDAO()
    }
}