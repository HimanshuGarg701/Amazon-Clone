package com.example.amazonn

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
            Toast.makeText(this, "Review Added", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ProductDescription::class.java)
            startActivity(intent)
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