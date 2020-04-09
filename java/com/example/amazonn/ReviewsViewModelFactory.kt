package com.example.amazonn

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReviewsViewModelFactory(private val reviewDao : ReviewDao,
                              private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ReviewsViewModel::class.java)){
            return ReviewsViewModel(reviewDao, application) as T
        }
        throw IllegalArgumentException("Failed to Create ViewModel")
    }
}