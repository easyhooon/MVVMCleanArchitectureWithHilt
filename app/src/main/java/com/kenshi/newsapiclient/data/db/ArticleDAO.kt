package com.kenshi.newsapiclient.data.db

import androidx.room.*
import com.kenshi.newsapiclient.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDAO {

    //we are going to use coroutines
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

    //we receive data queries from the room database, directly as live data
    //but there is a problem in that approach.
    //For MVVM Architecture
    //it is highly recommended not to use LiveData inside the repositories
    //Because LiveData is a lifecycle aware observable data holder
    //we should create or emit LiveData inside viewModels and observe them from views

    //Since repository does connected with a lifecycle, it is not recommended to use LiveData inside repositories
    //But now, fortunately room library allows us to get a data query as a coroutine flow
    //So, we can writes code to emit livedata from the flow inside the viewModel class
}