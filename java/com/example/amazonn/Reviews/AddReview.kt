package com.example.amazonn.Reviews

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.amazonn.*
import com.example.amazonn.AmazonnProducts.MainActivity
import com.example.amazonn.AmazonnProducts.Product
import com.example.amazonn.databinding.ActivityReviewsBinding
import kotlinx.coroutines.*

class AddReview : AppCompatActivity() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + job)
    private lateinit var binding : ActivityReviewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_reviews
        )

        val product = intent.getParcelableExtra<Product>("PRODUCT")
        binding.submitReview.setOnClickListener {
            val heading = binding.reviewHeading.toString()
            val reviewData = binding.reviewData.toString()

        }
    }


}