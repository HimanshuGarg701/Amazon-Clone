package com.example.amazonn

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.amazonn.databinding.ActivityReviewsBinding
import kotlinx.coroutines.*

class AddReview : AppCompatActivity() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var binding : ActivityReviewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reviews)

        val reviewHeading = binding.reviewHeading.text.toString()
        val reviewData = binding.reviewData.text.toString()

        val application = requireNotNull(this).application
        val review = Review(reviewHeading, reviewData)
        binding.submitReview.setOnClickListener {
            insertData(review, application)
        }
    }

    private fun insertData(review : Review, application : Application){
        scope.launch {
            withContext(Dispatchers.IO){
                val reviewDao = ReviewDatabase.getInstance(application).reviewDao
                reviewDao.insert(review)
            }
        }
    }
}