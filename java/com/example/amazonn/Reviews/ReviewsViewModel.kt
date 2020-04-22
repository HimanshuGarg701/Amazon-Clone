package com.example.amazonn.Reviews

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.amazonn.Reviews.ReviewDao

class ReviewsViewModel(private val reviewsDao : ReviewDao,
                       application: Application) : AndroidViewModel(application) {

    var reviews =null

}