package com.kenshi.newsapiclient.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.kenshi.newsapiclient.data.model.Article

@Dao
interface ArticleDAO {

    //we are going to use coroutines
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article){

    }
}