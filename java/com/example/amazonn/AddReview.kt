package com.example.amazonn

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.amazonn.databinding.ActivityReviewsBinding
import kotlinx.coroutines.*

class AddReview : AppCompatActivity() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + job)
    private lateinit var binding : ActivityReviewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reviews)

        val product = intent.getParcelableExtra<Product>("PRODUCT")
        binding.submitReview.setOnClickListener {
            val heading = binding.reviewHeading.toString()
            val reviewData = binding.reviewData.toString()
            val reviewDisplay = "$heading \n $reviewData"
            val review = Review(product.id, reviewDisplay)
            //val reviewString = TypeConvertor().objectToString(review)
            Log.d("ReviewDataBegin", reviewData.toString())
            Log.d("ReviewDataSecond", reviewDisplay)
            saveReviewToDatabase(product.id, review)
            val intent = Intent(this, MainActivity::class.java)
            Toast.makeText(this, "Thank you for the Review", Toast.LENGTH_SHORT).show()
            startActivity(intent)

        }
    }

    private fun saveReviewToDatabase(id : Int, review : Review){
        val thisApplication = requireNotNull(this).application
        uiScope.launch {
            storeReview(id, review, thisApplication)
        }
    }

    private suspend fun storeReview(id : Int, review : Review, application : Application){
        withContext(Dispatchers.IO){
            val reviewDao = ReviewDatabase.getInstance(application).reviewDao
            val reviews = reviewDao.getAllReviews(id)
            Log.d("ReviewDataInitial" , review.reviewData)
            val reviewData = TypeConvertor().objectToString(review.reviewData!!)
            Log.d("ReviewDataEnd" , review.reviewData)
            if(reviews==null || reviews.isEmpty()){
                review.reviewData = reviewData
                reviewDao.insert(review)
                Log.d("ReviewData", review.reviewData)
                Log.d("ReviewData", review.productId.toString())
            }else{
                val reviewsList = TypeConvertor().stringToObject(review.reviewData!!)
                reviewsList?.add(reviewData)
                val newReviewData = TypeConvertor().listObjectToString(reviewsList!!)
                review.reviewData = newReviewData
                reviewDao.updateReview(review)
            }
        }
    }
}