package com.kenshi.newsapiclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kenshi.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase

class NewsViewModelFactory (
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase
):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //construct a view model instance and return it as T.
        return NewsViewModel(
            app,
            getNewsHeadlinesUseCase
        ) as T
    }
}