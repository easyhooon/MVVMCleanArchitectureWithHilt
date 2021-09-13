package com.kenshi.newsapiclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kenshi.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.kenshi.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import com.kenshi.newsapiclient.domain.usecase.SaveNewsUseCase

class NewsViewModelFactory (
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase
):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //construct a view model instance and return it as T.
        return NewsViewModel(
            app,
            getNewsHeadlinesUseCase,
            getSearchedNewsUseCase,
            saveNewsUseCase
        ) as T
    }
}