package com.example.amazonn

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ReviewsViewModel(private val reviewsDao : ReviewDao,
                       application: Application) : AndroidViewModel(application) {

    val reviews = reviewsDao.getAllReviews()
}