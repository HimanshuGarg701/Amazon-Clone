package com.example.amazonn

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ReviewsViewModel(private val reviewsDao : ReviewDao,
                       application: Application) : AndroidViewModel(application) {

    var reviews : LiveData<List<Review>> = reviewsDao.getAllReviews()

}